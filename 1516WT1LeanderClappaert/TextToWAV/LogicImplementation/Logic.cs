using System;
using LogicInterfaces;
using DataAccessInterface;
using DataImplementation;
using DataEntities;
using System.Collections.Generic;

namespace LogicImplementation
{
    /// <summary>
    /// Logic flow of the program.
    /// </summary>
    public class Logic : ILogic
    {
        // connect with database files
        private IDataAccess backend = new DataAccess();

        // private variable(s)
        private string inputText;
        private Header header;
        private Format format;
        private Data data;

        /// <summary>
        /// Default constructor which initializes the Data Entities.
        /// </summary>
        public Logic()
        {
            this.header = new Header();
            this.format = new Format();
            this.data = new Data();
        }

        /// <summary>
        /// Get or set the path where the file needs to be stored.
        /// </summary>
        public string Path { get; set; }

        /// <summary>
        /// Get or set the filename.
        /// </summary>
        public string Filename { get; set; }

        /// <summary>
        /// Get or set the inputtext.
        /// </summary>
        public string InputText
        {
            get
            {
                return (inputText.ToLower());
            }
            set
            {
                inputText = value.ToLower();
            }
        }

        /// <summary>
        /// Check if the given filename input doens't have wrong symbols.
        /// </summary>
        /// <param name="file">filename which needs to be checked</param>
        /// <returns>true; filename is valid, false: filename is invalid</returns>
        public bool CheckFileName(string file)
        {
            if (file.Length == 0) return false;
            else
            {
                string fileInput = file.ToLower();
                for (int i = 0; i < fileInput.Length; i++)
                {
                    char ch = fileInput[i];
                    if (((ch > 96) && (ch < 123)) || ((ch > 47) && (ch < 58))) continue;
                    else return false;
                }

                return true;
            }
        }

        /// <summary>
        /// Check if the given text input doens't have wrong symbols.
        /// </summary>
        /// <param name="text">text which needs to be checked</param>
        /// <returns>true; text is valid, false: text is invalid</returns>
        public bool CheckTextInput(string text)
        {
            string checkInput = text.ToLower();
            for (int i = 0; i < checkInput.Length; i++)
            {
                char ch = checkInput[i];
                if (((ch > 96) && (ch < 123)) || (ch.Equals(' ')) || (ch.Equals('.')) || (ch.Equals('!')) || (ch.Equals('-'))) continue; //'a-z' or 'space' or '.' or '!' or '-'
                else return false;
            }

            return true;
        }

        /// <summary>
        /// The inputtext array converts into a frequency array, depending on the csv file.
        /// </summary>
        public int[] InputFrequencies
        {
            get
            {
                char[] csvChar = backend.CsvCharacters;
                int[] csvFreq = backend.CsvFrequencies;
                int[] freqListInput = new int[inputText.Length];

                for (int i = 0; i < inputText.Length; i++)
                {
                    for (int y = 0; y < csvChar.Length; y++)
                    {
                        if (inputText[i].Equals(csvChar[y]))
                        {
                            freqListInput[i] = csvFreq[y];
                            break;
                        }
                    }
                }

                return freqListInput;
            }
        }

        /// <summary>
        /// Make a new wav file from given input text.
        /// </summary>
        public void makeWavFile()
        {
            calculateSamples();
            backend.writeWav(header, format, data, Path, Filename);
        }

        /// <summary>
        /// Calculates the samples for each frequency.
        /// Based on the following blog article from microsoft:
        /// http://blogs.msdn.com/b/dawate/archive/2009/06/24/intro-to-audio-programming-part-3-synthesizing-simple-wave-audio-using-c.aspx
        /// Edited to function with an array of frequencies and to be stored into a list.
        /// </summary>
        /// <param name="header">contains all the header information</param>
        /// <param name="format">contains all the format information</param>
        /// <param name="data">contains all the data information</param>
        public void calculateSamples()
        {
            uint numSamples = format.SampleRate * format.NumChannels;
            int amplitude = 32760;  // Max amplitude for 16-bit audio
            List<short[]> listStorage = new List<short[]>();

            for (int h = 0; h < InputFrequencies.Length; h++)
            {
                // The "angle" used in the function, adjusted for the number of channels and sample rate.
                // This value is like the period of the wave.
                double t = (Math.PI * 2 * InputFrequencies[h]) / (format.SampleRate * format.NumChannels);
                // Initialize the 16-bit array
                short[] array = new short[numSamples];

                for (uint i = 0; i < numSamples - 1; i++) //-1, because the array, used in this loop, starts at position 0.
                {
                    // Fill with a simple sine wave at max amplitude
                    for (int channel = 0; channel < format.NumChannels; channel++)
                    {
                        array[i + channel] = Convert.ToInt16(amplitude * Math.Sin(t * i));
                    }
                }

                listStorage.Add(array);
            }

            data.SubChunk2Size = (listStorage.Count * listStorage[0].Length) * (format.BitsPerSample / 8); // Calculate data chunk size in bytes
            header.ChunkSize = (uint)data.SubChunk2Size; // Store in header class
            data.SoundData = listStorage; // Store in data class
        }
    }
}
