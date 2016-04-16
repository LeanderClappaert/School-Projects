using System;
using System.Linq;
using DataAccessInterface;
using System.IO;
using DataEntities;
using System.Collections.Generic;

namespace DataImplementation
{    /// <summary>
    /// Read from and write to files.
    /// </summary>
    public class DataAccess : IDataAccess
    {
        public Dictionary<string, int> ReadCsvFile()
        {
            var reader = File.ReadAllText(@"..\..\..\DataImplementation\CSV\TextFreqFile.csv").Split('\n');
            var csv = from line in reader select line.Split(',').ToArray();

            int headerRows = 1; // number of headerRows to be skipped
            int i = 0; // costum iterator for foreach loop

            // get character values from csv file
            foreach (var row in csv.Skip(headerRows).TakeWhile(r => r.Length > 1 && r.Last().Trim().Length > 0))
            {
                chars[i] = row[0];
                i++;
            }

            i = 0; //reset costum iterator for next foreach loop
            foreach (var convert in chars)
            {
                ch[i] = convert[0];
                i++;
            }
        }

        /// <summary>
        /// Get the characters from the csv file (first column in the file).
        /// I used a method found on stackoverflow, user 'tomsv': http://stackoverflow.com/questions/5282999/reading-csv-file-and-storing-values-into-an-array
        /// Furthermore, I converted the outcome to the right type.
        /// </summary>
        public char[] CsvCharacters
        {
            get
            {
                string[] chars = new string[30];
                char[] ch = new char[30];

                var reader = File.ReadAllText(@"..\..\..\DataImplementation\CSV\TextFreqFile.csv").Split('\n');
                var csv = from line in reader select line.Split(',').ToArray();

                int headerRows = 1; //number of headerRows to be skipped
                int i = 0; //costum iterator for foreach loop

                // get character values from csv file
                foreach (var row in csv.Skip(headerRows).TakeWhile(r => r.Length > 1 && r.Last().Trim().Length > 0))
                {
                    chars[i] = row[0];
                    i++;
                }

                i = 0; //reset costum iterator for next foreach loop
                foreach (var convert in chars)
                {
                    ch[i] = convert[0];
                    i++;
                }

                return ch;
            }
        }

        /// <summary>
        /// Get the frequencies from the csv file (second column in the file).
        /// I used the same method, found on stackoverflow, user 'tomsv': http://stackoverflow.com/questions/5282999/reading-csv-file-and-storing-values-into-an-array
        /// Furthermore, I converted the outcome to the right type.
        /// </summary>
        public int[] CsvFrequencies
        {
            get
            {
                string[] freqs = new string[30];
                int[] fr = new int[30];

                var reader = File.ReadAllText(@"..\..\..\DataImplementation\CSV\TextFreqFile.csv").Split('\n');
                var csv = from line in reader select line.Split(',').ToArray();

                int headerRows = 1; // number of headerRows to be skipped
                int i = 0; // costum iterator for foreach loop

                // get frequency values from csv file
                foreach (var row in csv.Skip(headerRows).TakeWhile(r => r.Length > 1 && r.Last().Trim().Length > 0))
                {
                    freqs[i] = row[1];
                    i++;
                }

                // convert string to integer.
                for (i = 0; i < freqs.Length; i++)
                {
                    fr[i] = Int32.Parse(freqs[i]);
                }

                return fr;
            }
        }

        /// <summary>
        /// Write all the information into 1 *.wav file.
        /// </summary>
        /// <param name="header">contains all the header information</param>
        /// <param name="format">contains all the format information</param>
        /// <param name="data">contains all the data information</param>
        public void writeWav(Header header, Format format, Data data, string path, string filename)
        {
            string storefile = path + "\\" + filename + ".wav";
            using (var file = new FileStream(storefile, FileMode.Create))
            {
                using (var writer = new BinaryWriter(file))
                {
                    // header part
                    writer.Write(header.ChunkID);
                    writer.Write(header.ChunkSize);
                    writer.Write(header.Format);
                    // format part
                    writer.Write(format.SubChunk1ID);
                    writer.Write(format.SubChunk1Size);
                    writer.Write(format.AudioFormat);
                    writer.Write(format.NumChannels);
                    writer.Write(format.SampleRate);
                    writer.Write(format.ByteRate);
                    writer.Write(format.BlockALign);
                    writer.Write(format.BitsPerSample);
                    // data part
                    writer.Write(data.SubChunk2ID);
                    writer.Write(data.SubChunk2Size);

                    foreach (var shortArray in data.SoundData)
                    {
                        foreach (var value in shortArray)
                        {
                            writer.Write(value);
                        }
                    }

                    writer.Seek(4, SeekOrigin.Begin);
                    uint fileSize = (uint)writer.BaseStream.Length;
                    writer.Write(fileSize - 8); //or = sound data + offset 44
                }
            }
        }
    }
}
