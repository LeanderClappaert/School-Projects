using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace LogicInterfaces
{
    public interface ILogic
    {

        /// <summary>
        /// Check if the choosen file is a wav file
        /// </summary>
        /// <param name="path">Path where wav file is located</param>
        /// <returns>True or false</returns>
        bool CheckChosenWavFile(string path);

        /// <summary>
        /// Check if the choosen file is a csv file
        /// </summary>
        /// <param name="path">Path where csv file is located</param>
        /// <returns>True or false</returns>
        bool CheckChosenCsvFile(string path);

        /// <summary>
        /// Get the frequencies from the wav file
        /// </summary>
        /// <param name="wavPath">Path where wav file is located</param>
        /// <param name="csvPath">Path where csv file is located</param>
        /// <returns>Integer array with frequencies</returns>
        int[] GetFrequencies(string wavPath, string csvPath);

        /// <summary>
        /// Get the hidden text by comparing found frequencies with the frequencies listed in the csv file
        /// </summary>
        /// <param name="frequencies">found frequencies</param>
        /// <param name="csvPath">Path where csv file is located</param>
        /// <returns>The hidden text</ret
        string GetSecretText(int[] frequencies, string csvPath);

        // events
        event Action<string> CsvError;
        event Action<string> WavError;
    }
}
