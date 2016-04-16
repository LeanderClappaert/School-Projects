﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace FourierLib
{
    #region FourierDirection
    /// <summary>
    /// <p>The direction of the fourier transform.</p>
    /// </summary>
    public enum FourierDirection : int
    {
        /// <summary>
        /// Forward direction.  Usually in reference to moving from temporal
        /// representation to frequency representation
        /// </summary>
        Forward = 1,
        /// <summary>
        /// Backward direction. Usually in reference to moving from frequency
        /// representation to temporal representation
        /// </summary>
        Backward = -1,
    }
    #endregion

    #region Fourier

    public class Fourier
    {
        #region Internal logic

        static private void Swap(ref float a, ref float b)
        {
            float temp = a;
            a = b;
            b = temp;
        }
        static private void Swap(ref double a, ref double b)
        {
            double temp = a;
            a = b;
            b = temp;
        }

        //-------------------------------------------------------------------------------------

        private const int cMaxBits = 31;
        private const int cMinBits = 0;


        static public bool IsPowerOf2(long x)
        {
            return (x & (x - 1)) == 0;
            //return	( x == Pow2( Log2( x ) ) );
        }
        static public long Pow2(int exponent)
        {
            if (exponent >= 0 && exponent < 63)
            {
                return (long)1 << exponent;
            }
            return 0;
        }
        static public int Log2(long x)
        {
            if (x <= 65536)
            {
                if (x <= 256)
                {
                    if (x <= 16)
                    {
                        if (x <= 4)
                        {
                            if (x <= 2)
                            {
                                if (x <= 1)
                                {
                                    return 0;
                                }
                                return 1;
                            }
                            return 2;
                        }
                        if (x <= 8)
                            return 3;
                        return 4;
                    }
                    if (x <= 64)
                    {
                        if (x <= 32)
                            return 5;
                        return 6;
                    }
                    if (x <= 128)
                        return 7;
                    return 8;
                }
                if (x <= 4096)
                {
                    if (x <= 1024)
                    {
                        if (x <= 512)
                            return 9;
                        return 10;
                    }
                    if (x <= 2048)
                        return 11;
                    return 12;
                }
                if (x <= 16384)
                {
                    if (x <= 8192)
                        return 13;
                    return 14;
                }
                if (x <= 32768)
                    return 15;
                return 16;
            }
            if (x <= 16777216)
            {
                if (x <= 1048576)
                {
                    if (x <= 262144)
                    {
                        if (x <= 131072)
                            return 17;
                        return 18;
                    }
                    if (x <= 524288)
                        return 19;
                    return 20;
                }
                if (x <= 4194304)
                {
                    if (x <= 2097152)
                        return 21;
                    return 22;
                }
                if (x <= 8388608)
                    return 23;
                return 24;
            }
            if (x <= 268435456)
            {
                if (x <= 67108864)
                {
                    if (x <= 33554432)
                        return 25;
                    return 26;
                }
                if (x <= 134217728)
                    return 27;
                return 28;
            }
            if (x <= 1073741824)
            {
                if (x <= 536870912)
                    return 29;
                return 30;
            }
            //	since int is unsigned it can never be higher than 2,147,483,647
            //	if( x <= 2147483648 )
            //		return	31;	
            //	return	32;	
            return 31;
        }

        //-------------------------------------------------------------------------------------
        //-------------------------------------------------------------------------------------

        static public int ReverseBits(int index, int numberOfBits)
        {
            //Debug.Assert(numberOfBits >= cMinBits);
            //Debug.Assert(numberOfBits <= cMaxBits);

            int reversedIndex = 0;
            for (int i = 0; i < numberOfBits; i++)
            {
                reversedIndex = (reversedIndex << 1) | (index & 1);
                index = (index >> 1);
            }
            return reversedIndex;
        }

        //-------------------------------------------------------------------------------------

        static private int[][] _reversedBits = new int[cMaxBits][];
        static private int[] GetReversedBits(int numberOfBits)
        {
            //Debug.Assert(numberOfBits >= cMinBits);
            //Debug.Assert(numberOfBits <= cMaxBits);
            if (_reversedBits[numberOfBits - 1] == null)
            {
                long maxBits = Fourier.Pow2(numberOfBits);
                int[] reversedBits = new int[maxBits];
                for (int i = 0; i < maxBits; i++)
                {
                    int oldBits = i;
                    int newBits = 0;
                    for (int j = 0; j < numberOfBits; j++)
                    {
                        newBits = (newBits << 1) | (oldBits & 1);
                        oldBits = (oldBits >> 1);
                    }
                    reversedBits[i] = newBits;
                }
                _reversedBits[numberOfBits - 1] = reversedBits;
            }
            return _reversedBits[numberOfBits - 1];
        }

        //-------------------------------------------------------------------------------------

        static private void ReorderArray(float[] data)
        {
            //Debug.Assert(data != null);

            int length = data.Length / 2;

            //Debug.Assert(Fourier.IsPowerOf2(length) == true);
            //Debug.Assert(length >= cMinLength);
            //Debug.Assert(length <= cMaxLength);

            int[] reversedBits = Fourier.GetReversedBits(Fourier.Log2(length));
            for (int i = 0; i < length; i++)
            {
                int swap = reversedBits[i];
                if (swap > i)
                {
                    Fourier.Swap(ref data[(i << 1)], ref data[(swap << 1)]);
                    Fourier.Swap(ref data[(i << 1) + 1], ref data[(swap << 1) + 1]);
                }
            }
        }

        static private void ReorderArray(double[] data)
        {
            //Debug.Assert(data != null);

            int length = data.Length / 2;

            //Debug.Assert(Fourier.IsPowerOf2(length) == true);
            //Debug.Assert(length >= cMinLength);
            //Debug.Assert(length <= cMaxLength);

            int[] reversedBits = Fourier.GetReversedBits(Fourier.Log2(length));
            for (int i = 0; i < length; i++)
            {
                int swap = reversedBits[i];
                if (swap > i)
                {
                    Fourier.Swap(ref data[i << 1], ref data[swap << 1]);
                    Fourier.Swap(ref data[i << 1 + 1], ref data[swap << 1 + 1]);
                }
            }
        }

        //======================================================================================

        private static int[][] _reverseBits = null;

        private static int _ReverseBits(int bits, int n)
        {
            int bitsReversed = 0;
            for (int i = 0; i < n; i++)
            {
                bitsReversed = (bitsReversed << 1) | (bits & 1);
                bits = (bits >> 1);
            }
            return bitsReversed;
        }

        private static void InitializeReverseBits(int levels)
        {
            _reverseBits = new int[levels + 1][];
            for (int j = 0; j < (levels + 1); j++)
            {
                int count = (int)Math.Pow(2, j);
                _reverseBits[j] = new int[count];
                for (int i = 0; i < count; i++)
                {
                    _reverseBits[j][i] = _ReverseBits(i, j);
                }
            }
        }

        private static int _lookupTabletLength = -1;
        private static double[,][] _uRLookup = null;
        private static double[,][] _uILookup = null;
        private static float[,][] _uRLookupF = null;
        private static float[,][] _uILookupF = null;

        private static void SyncLookupTableLength(int length)
        {
            //Debug.Assert(length < 1024 * 10);
            //Debug.Assert(length >= 0);
            if (length > _lookupTabletLength)
            {
                int level = (int)Math.Ceiling(Math.Log(length, 2));
                Fourier.InitializeReverseBits(level);
                Fourier.InitializeComplexRotations(level);
                //_cFFTData	= new Complex[ Math2.CeilingBase( length, 2 ) ];
                //_cFFTDataF	= new ComplexF[ Math2.CeilingBase( length, 2 ) ];
                _lookupTabletLength = length;
            }
        }

        private static int GetLookupTableLength()
        {
            return _lookupTabletLength;
        }

        private static void ClearLookupTables()
        {
            _uRLookup = null;
            _uILookup = null;
            _uRLookupF = null;
            _uILookupF = null;
            _lookupTabletLength = -1;
        }

        private static void InitializeComplexRotations(int levels)
        {
            int ln = levels;
            //_wRLookup = new float[ levels + 1, 2 ];
            //_wILookup = new float[ levels + 1, 2 ];

            _uRLookup = new double[levels + 1, 2][];
            _uILookup = new double[levels + 1, 2][];

            _uRLookupF = new float[levels + 1, 2][];
            _uILookupF = new float[levels + 1, 2][];

            int N = 1;
            for (int level = 1; level <= ln; level++)
            {
                int M = N;
                N <<= 1;

                //float scale = (float)( 1 / Math.Sqrt( 1 << ln ) );

                // positive sign ( i.e. [M,0] )
                {
                    double uR = 1;
                    double uI = 0;
                    double angle = (double)Math.PI / M * 1;
                    double wR = (double)Math.Cos(angle);
                    double wI = (double)Math.Sin(angle);

                    _uRLookup[level, 0] = new double[M];
                    _uILookup[level, 0] = new double[M];
                    _uRLookupF[level, 0] = new float[M];
                    _uILookupF[level, 0] = new float[M];

                    for (int j = 0; j < M; j++)
                    {
                        _uRLookupF[level, 0][j] = (float)(_uRLookup[level, 0][j] = uR);
                        _uILookupF[level, 0][j] = (float)(_uILookup[level, 0][j] = uI);
                        double uwI = uR * wI + uI * wR;
                        uR = uR * wR - uI * wI;
                        uI = uwI;
                    }
                }
                {

                    // negative sign ( i.e. [M,1] )
                    double uR = 1;
                    double uI = 0;
                    double angle = (double)Math.PI / M * -1;
                    double wR = (double)Math.Cos(angle);
                    double wI = (double)Math.Sin(angle);

                    _uRLookup[level, 1] = new double[M];
                    _uILookup[level, 1] = new double[M];
                    _uRLookupF[level, 1] = new float[M];
                    _uILookupF[level, 1] = new float[M];

                    for (int j = 0; j < M; j++)
                    {
                        _uRLookupF[level, 1][j] = (float)(_uRLookup[level, 1][j] = uR);
                        _uILookupF[level, 1][j] = (float)(_uILookup[level, 1][j] = uI);
                        double uwI = uR * wI + uI * wR;
                        uR = uR * wR - uI * wI;
                        uI = uwI;
                    }
                }

            }
        }

        #endregion

        #region Public functions
        /// <summary>
        /// Compute a 1D fast Fourier transform of a dataset of complex numbers (as pairs of float's).
        /// </summary>
        /// <param name="data"></param>
        /// <param name="length"></param>
        /// <param name="direction"></param>
        public static void FFT(float[] data, int length, FourierDirection direction)
        {
            //Debug.Assert(data != null);
            //Debug.Assert(data.Length >= length * 2);
            //Debug.Assert(Fourier.IsPowerOf2(length) == true);

            Fourier.SyncLookupTableLength(length);

            int ln = Fourier.Log2(length);

            // reorder array
            Fourier.ReorderArray(data);

            // successive doubling
            int N = 1;
            int signIndex = (direction == FourierDirection.Forward) ? 0 : 1;
            for (int level = 1; level <= ln; level++)
            {
                int M = N;
                N <<= 1;

                float[] uRLookup = _uRLookupF[level, signIndex];
                float[] uILookup = _uILookupF[level, signIndex];

                for (int j = 0; j < M; j++)
                {
                    float uR = uRLookup[j];
                    float uI = uILookup[j];

                    for (int evenT = j; evenT < length; evenT += N)
                    {
                        int even = evenT << 1;
                        int odd = (evenT + M) << 1;

                        float r = data[odd];
                        float i = data[odd + 1];

                        float odduR = r * uR - i * uI;
                        float odduI = r * uI + i * uR;

                        r = data[even];
                        i = data[even + 1];

                        data[even] = r + odduR;
                        data[even + 1] = i + odduI;

                        data[odd] = r - odduR;
                        data[odd + 1] = i - odduI;
                    }
                }
            }
        }

        /// <summary>
        /// Compute a 1D real-symmetric fast fourier transform.
        /// </summary>
        /// <param name="data"></param>
        /// <param name="direction"></param>
        public static void RFFT(float[] data, FourierDirection direction)
        {
            if (data == null)
            {
                throw new ArgumentNullException("data");
            }
            Fourier.RFFT(data, data.Length, direction);
        }

        /// <summary>
        /// Compute a 1D real-symmetric fast fourier transform.
        /// </summary>
        /// <param name="data"></param>
        /// <param name="length"></param>
        /// <param name="direction"></param>
        public static void RFFT(float[] data, int length, FourierDirection direction)
        {
            if (data == null)
            {
                throw new ArgumentNullException("data");
            }
            if (data.Length < length)
            {
                throw new ArgumentOutOfRangeException("length", length, "must be at least as large as 'data.Length' parameter");
            }
            if (Fourier.IsPowerOf2(length) == false)
            {
                throw new ArgumentOutOfRangeException("length", length, "must be a power of 2");
            }

            float c1 = 0.5f, c2;
            float theta = (float)Math.PI / (length / 2);

            if (direction == FourierDirection.Forward)
            {
                c2 = -0.5f;
                FFT(data, length / 2, direction);
            }
            else
            {
                c2 = 0.5f;
                theta = -theta;
            }

            float wtemp = (float)Math.Sin(0.5 * theta);
            float wpr = -2 * wtemp * wtemp;
            float wpi = (float)Math.Sin(theta);
            float wr = 1 + wpr;
            float wi = wpi;

            // do / undo packing
            for (int i = 1; i < length / 4; i++)
            {
                int a = 2 * i;
                int b = length - 2 * i;
                float h1r = c1 * (data[a] + data[b]);
                float h1i = c1 * (data[a + 1] - data[b + 1]);
                float h2r = -c2 * (data[a + 1] + data[b + 1]);
                float h2i = c2 * (data[a] - data[b]);
                data[a] = h1r + wr * h2r - wi * h2i;
                data[a + 1] = h1i + wr * h2i + wi * h2r;
                data[b] = h1r - wr * h2r + wi * h2i;
                data[b + 1] = -h1i + wr * h2i + wi * h2r;
                wr = (wtemp = wr) * wpr - wi * wpi + wr;
                wi = wi * wpr + wtemp * wpi + wi;
            }

            if (direction == FourierDirection.Forward)
            {
                float hir = data[0];
                data[0] = hir + data[1];
                data[1] = hir - data[1];
            }
            else
            {
                float hir = data[0];
                data[0] = c1 * (hir + data[1]);
                data[1] = c1 * (hir - data[1]);
                Fourier.FFT(data, length / 2, direction);
            }
        }
        #endregion
    
    }

    #endregion
}
