using System;

namespace DataEntities
{
    /// <summary>
    /// The "RIFF" chunk descriptor.
    /// </summary>
    public struct Header
    {
        private uint subChunk2Size;

        /// <summary>
        /// Contains the letters "RIFF" in ASCII form.
        /// </summary>
        public char[] ChunkID
        {
            get
            {
                return ("RIFF".ToCharArray());
            }
        }

        /// <summary>
        /// The size of the rest of the cunk following this number.
        /// </summary>
        public uint ChunkSize
        {
            get
            {
                // 36 = offset 40 from subChunk2Size - offset 4 from start chunkSize.
                return (subChunk2Size + 36);
            }
            set
            {
                subChunk2Size = value;
                Console.WriteLine(value);
            }
        }

        /// <summary>
        /// Contains the letters "WAVE" in ASCII form.
        /// </summary>
        public char[] Format
        {
            get
            {
                return ("WAVE".ToCharArray());
            }
        }
    }
}
