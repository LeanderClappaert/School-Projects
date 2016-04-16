using System;
using System.Collections.Generic;
using System.Linq;
using DataAccessInterfaces;
using System.IO;

namespace DataAccessImplementation
{
    public class DataAccess : IDataAccess
    {
        //events
        public event Action<string> CsvError;
        public event Action<string> WavError;

        //private variables
        private byte[] dataBytes;
        private int bitDepth;
        private int channels;
        private int sampleRate;

        /// <summary>
        /// Read a csv file and store data in a dictionary for further use
        /// Trigger an error event when the format of the data isn't correct
        /// Source: user 'tomsv': http://stackoverflow.com/questions/5282999/reading-csv-file-and-storing-values-into-an-array
        /// </summary>
        /// <param name="csvPath">Path where csv file is located</param>
        /// <returns>Dictionary which contains all csv file data</returns>
        public Dictionary<char, int> ReadCsvFile(string csvPath)
        {
            IProgress<string> csvProgressError = new Progress<string>((p) =>
            {
                if (CsvError != null) CsvError(p);
            });

            Dictionary<char, int> csvFile = new Dictionary<char, int>();

            var reader = File.ReadAllText(csvPath).Split('\n');
            var csv = from line in reader select line.Split(',').ToArray();
            int headerRows = 0; // number of headerRows to be skipped

            try
            {
                // get character values from csv file
                foreach (var row in csv.Skip(headerRows).TakeWhile(r => r.Length > 1 && r.Last().Trim().Length > 0))
                {
                    csvFile.Add(char.Parse(row[0]), Int32.Parse(row[1]));
                }
            }
            catch(Exception e)
            {
                csvProgressError.Report("Het csv bestand heeft een ongeldige indeling van de data!");
            }

            return csvFile;
        }

        /// <summary>
        /// Read the entire wav file and initialize the private variables
        /// Trigger an error event when the format of the data isn't correct
        /// Source: https://msdn.microsoft.com/en-us/library/ff827591.aspx
        /// </summary>
        /// <param name="wavPath">Path where wav file is located</param>
        public void ReadWavFile(string wavPath)
        {
            // header
            int chunkID, fileSize, riffType;
            // format
            int fmtID, fmtSize, fmtCode, /*channels, sampleRate,*/ fmtAvgBPS, fmtBlockAlign, /*bitDepth, */fmtExtraSize;
            // data
            int dataID, dataSize;

            IProgress<string> wavProgressError = new Progress<string>((p) =>
            {
                if (WavError != null) WavError(p);
            });

            try
            {
                using (var waveFileStream = new FileStream(wavPath, FileMode.Open))
                {
                    using (var reader = new BinaryReader(waveFileStream))
                    {
                        chunkID = reader.ReadInt32();
                        fileSize = reader.ReadInt32();
                        riffType = reader.ReadInt32();
                        fmtID = reader.ReadInt32();
                        fmtSize = reader.ReadInt32();
                        fmtCode = reader.ReadInt16();
                        channels = reader.ReadInt16();
                        sampleRate = reader.ReadInt32();
                        fmtAvgBPS = reader.ReadInt32();
                        fmtBlockAlign = reader.ReadInt16();
                        bitDepth = reader.ReadInt16();

                        if (fmtSize == 18)
                        {
                            // Read any extra values
                            fmtExtraSize = reader.ReadInt16();
                            reader.ReadBytes(fmtExtraSize);
                        }

                        dataID = reader.ReadInt32();
                        dataSize = reader.ReadInt32();
                        dataBytes = reader.ReadBytes(dataSize);
                    }
                }
            }
            catch (Exception e)
            {
                wavProgressError.Report("Het wav bestand heeft een ongeldige indeling van de data!");
            }
        }

        /// <summary>
        /// Get the sample rate from the wav file
        /// </summary>
        public int SampleRate
        {
            get
            {
                return sampleRate;
            }
        }

        /// <summary>
        /// Get the number of channels used to code the wav file
        /// </summary>
        public int Channels
        {
            get
            {
                return channels;
            }
        }

        /// <summary>
        /// Get the bits per sample from the wav file
        /// </summary>
        public int BitDepth
        {
            get
            {
                return bitDepth;
            }
        }

        /// <summary>
        /// Get the data from the wav file
        /// </summary>
        public byte[] DataBytes
        {
            get
            {
                return dataBytes;
            }
        }

        /// <summary>
        /// Convert the data from a byte array to an float array, depending on the channels and bits per sample
        /// </summary>
        /// <param name="data">data which needs to be converted</param>
        /// <returns>float array with the same, but converted, data</returns>
        public float[] DataToFloat(byte[] data)
        {
            byte[] filteredData;

            //depending on number of channels, half the data will be erased
            if (Channels == 2)
            {
                filteredData = new byte[data.Length / 2];
                filteredData = FilterChannels();
            }
            else //channels == 1
            {
                filteredData = data;
            }

            //depending on BitsPerSample (= BitDepth), number of bytes to be used for converting varies
            int numBytesInNumber = 0;
            float[] floatData;
            int y = 0; //custom iterator

            switch (BitDepth)
            {
                case 8:
                    numBytesInNumber = 1;
                    floatData = new float[filteredData.Length / numBytesInNumber];

                    for (int i = 0; i < filteredData.Length; i += numBytesInNumber)
                    {
                        floatData[y] = filteredData[i];
                        y++;
                    }

                    break;

                case 16:
                    numBytesInNumber = 2;
                    floatData = new float[filteredData.Length / numBytesInNumber];

                    for (int i = 0; i < filteredData.Length; i += numBytesInNumber)
                    {
                        floatData[y] = BitConverter.ToInt16(filteredData, i);
                        y++;
                    }

                    break;

                case 32:
                    numBytesInNumber = 4;
                    floatData = new float[filteredData.Length / numBytesInNumber];

                    for (int i = 0; i < filteredData.Length; i += numBytesInNumber)
                    {
                        floatData[y] = BitConverter.ToInt32(filteredData, i);
                        y++;
                    }

                    break;

                default:
                    floatData = new float[0];
                    break;
            }

            return floatData;
        }

        /// <summary>
        /// If 2 channels were used, delete half the data (= double data)
        /// </summary>
        /// <returns>Array with half the byte data as before</returns>
        private byte[] FilterChannels()
        {
            byte[] filteredData = new byte[dataBytes.Length / 2];
            int y = 0; //custom iterator

            for (int i = 0; i < dataBytes.Length; i++)
            {
                if (i % 2 != 0)
                {
                    filteredData[y] = dataBytes[i];
                    y++;
                }
            }

            return filteredData;
        }
    }
}
