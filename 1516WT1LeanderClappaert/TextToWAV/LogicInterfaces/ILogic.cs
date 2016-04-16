namespace LogicInterfaces
{
    /// <summary>
    /// Interface for Logic Implementation.
    /// </summary>
    public interface ILogic
    {
        /// <summary>
        /// Get or set the input text.
        /// </summary>
        string InputText { get; set; }

        /// <summary>
        /// Get or set the path which the user choses.
        /// </summary>
        string Path { get; set; }

        /// <summary>
        /// Get or set the filename which the user choses.
        /// </summary>
        string Filename { get; set; }

        /// <summary>
        /// Check if filename has correct format.
        /// </summary>
        /// <param name="text">Input filename from the user</param>
        /// <returns>True if correct, false if not</returns>
        bool CheckFileName(string file);

        /// <summary>
        /// Check if GUI input is correct.
        /// </summary>
        /// <param name="text">Input text from the user</param>
        /// <returns>True if correct, false if not</returns>
        bool CheckTextInput(string text);

        /// <summary>
        /// The inputtext array converts into a frequency array, depending on the csv file.
        /// </summary>
        int[] InputFrequencies { get; }

        /// <summary>
        /// Make a new wav file from given input text.
        /// </summary>
        void makeWavFile();

        /// <summary>
        /// Calculates the samples for each frequency.
        /// </summary>
        void calculateSamples();
    }
}
