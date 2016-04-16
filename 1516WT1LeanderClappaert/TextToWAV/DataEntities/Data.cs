using System.Collections.Generic;

namespace DataEntities
{
    /// <summary>
    /// The "data" sub-chunk.
    /// </summary>
    public struct Data
    {
        /// <summary>
        /// Contains the letters "data " in ASCII form.
        /// </summary>
        public char[] SubChunk2ID
        {
            get
            {
                return ("data".ToCharArray());
            }
        }

        /// <summary>
        /// The number of bytes in the data.
        /// = numSamples * numChannels * bitsPerSample / 8.
        /// </summary>
        public int SubChunk2Size { get; set; }

        /// <summary>
        /// The actual sound data.
        /// </summary>
        public List<short[]> SoundData { get; set; }
    }
}
