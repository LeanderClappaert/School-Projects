namespace DataEntities
{
    /// <summary>
    /// The "fmt " sub-chunk.
    /// </summary>
    public struct Format
    {
        /// <summary>
        /// Contains the letters "fmt " in ASCII form.
        /// </summary>
        public char[] SubChunk1ID
        {
            get
            {
                return ("fmt ".ToCharArray()); // space behind fmt.
            }
        }

        /// <summary>
        /// Return 16 for PCM use.
        /// </summary>
        public uint SubChunk1Size
        {
            get
            {
                return 16;
            }
        }

        /// <summary>
        /// Return 1 for PCM (Linear quantization, other values indicates some sort of compression).
        /// </summary>
        public ushort AudioFormat
        {
            get
            {
                return 1;
            }
        }

        /// <summary>
        /// Return 1 for mono, 2 for stereo.
        /// </summary>
        public ushort NumChannels
        {
            get
            {
                return 2;
            }
        }

        /// <summary>
        /// Samples per second, in Hertz
        /// </summary>
        public uint SampleRate
        {
            get
            {
                return 44100;
            }
        }

        /// <summary>
        /// Number of bits per sample, here 16 bits.
        /// </summary>
        public ushort BitsPerSample
        {
            get
            {
                return 16;
            }
        }

        /// <summary>
        /// Number of bytes that are processed per second.
        /// </summary>
        public int ByteRate
        {
            get
            {
                return ((int)(SampleRate * NumChannels * BitsPerSample / 8));
            }
        }

        /// <summary>
        /// The number of bytes for one sample including all channels.
        /// </summary>
        public ushort BlockALign
        {
            get
            {
                return ((ushort)(NumChannels * (BitsPerSample / 8)));
            }
        }

    }
}
