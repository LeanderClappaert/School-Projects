using System;
using System.Collections.Generic;

namespace DataAccessInterfaces
{
    public interface IDataAccess
    {
        /// <summary>
        /// Read a csv file and store data in a dictionary for further use
        /// </summary>
        /// <param name="csvPath">Path where csv file is located</param>
        /// <returns>Dictionary which contains all csv file data</returns>
        Dictionary<char, int> ReadCsvFile(string csvPath);

        /// <summary>
        /// Read the entire wav file and initialize the private variables
        /// </summary>
        /// <param name="wavPath">Path where wav file is located</param>
        void ReadWavFile(string wavPath);

        /// <summary>
        /// Get the bits per sample from the wav file
        /// </summary>
        int BitDepth { get; }

        /// <summary>
        /// Get the number of channels used to code the wav file
        /// </summary>
        int Channels { get; }

        /// <summary>
        /// Get the sample rate from the wav file
        /// </summary>
        int SampleRate { get; }

        /// <summary>
        /// Get the data from the wav file
        /// </summary>
        byte[] DataBytes { get; }

        /// <summary>
        /// Convert the data from a byte array to an float array, depending on the channels and bits per sample
        /// </summary>
        /// <param name="data">data which needs to be converted</param>
        /// <returns>float array with the same, but converted, data</returns>
        float[] DataToFloat(byte[] data);

        //events
        event Action<string> CsvError;
        event Action<string> WavError;
    }
}
