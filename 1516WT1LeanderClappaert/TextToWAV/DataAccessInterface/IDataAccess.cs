using DataEntities;

namespace DataAccessInterface
{
    /// <summary>
    /// Interface for Data Access Implementation
    /// </summary>
    public interface IDataAccess
    {
        /// <summary>
        /// Get array of characters from the *.csv file.
        /// </summary>
        char[] CsvCharacters { get; }

        /// <summary>
        /// Get the array of frequencies from the *.csv file.
        /// </summary>
        int[] CsvFrequencies { get; }

        /// <summary>
        /// Write the *.wav file using all the information from the Header, Format and Data structs.
        /// </summary>
        /// <param name="header">contains all the header information</param>
        /// <param name="format">contains all the format information</param>
        /// <param name="data">contains all the data information</param>
        void writeWav(Header header, Format format, Data data, string path, string filename);
    }
}
