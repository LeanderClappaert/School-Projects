using System;
using System.Windows.Forms;
using LogicImplementation;

namespace GUI
{
    public partial class Form1 : Form
    {
        // private connection with logic
        private Logic logic;

        // private variables
        private string wavPath;
        private string csvPath;
        private bool wavOK = false;
        private bool csvOK = false;
        private bool resetKey = true;

        /// <summary>
        /// Initialize components and show basic form setup
        /// </summary>
        public Form1()
        {
            InitializeComponent();
            logic = new Logic();
            labelWavError.Visible = false;
            labelWavResult.Visible = false;
            labelCsvError.Visible = false;
            labelCsvResult.Visible = false;
            labelEventErrors.Visible = false;
            buttonStart.Enabled = false;

            // events
            logic.CsvError += Logic_CsvErrorHandler;
            logic.WavError += Logic_WavErrorHandler;
        }

        /// <summary>
        /// Eventhandler which displays an error if csv file has the wrong format
        /// </summary>
        /// <param name="error">The custom error which has been generated</param>
        private void Logic_CsvErrorHandler(string error)
        {
            labelEventErrors.Text = error;
            labelEventErrors.Visible = true;
            textBoxResult.Text = "";
        }

        /// <summary>
        /// Eventhandler which displays an error if the wav file has the wrong format
        /// </summary>
        /// <param name="error">The custom error which has been generated</param>
        private void Logic_WavErrorHandler(string error)
        {
            labelEventErrors.Text = error;
            labelEventErrors.Visible = true;
            textBoxResult.Text = "";
        }

        /// <summary>
        /// Select wav file from disk
        /// Only extension .wav is allowed
        /// </summary>
        private void buttonSelectWav_Click(object sender, EventArgs e)
        {
            if (openFileDialogWav.ShowDialog() == DialogResult.OK)
            {
                wavPath = openFileDialogWav.FileName;
                string wavFileName = openFileDialogWav.SafeFileName;        

                if (!logic.CheckChosenWavFile(wavPath))
                {
                    labelWavError.Visible = true;
                    labelWavResult.Text = "";
                    wavOK = false;
                }
                else
                {
                    labelWavError.Visible = false;
                    labelWavResult.Text = wavFileName;
                    labelWavResult.Visible = true;
                    wavOK = true;
                }

                if (csvOK && wavOK) buttonStart.Enabled = true;
                else buttonStart.Enabled = false;
            }
        }

        /// <summary>
        /// Select csv file from disk
        /// Only extension .csv is allowed
        /// </summary>
        private void buttonSelectCsv_Click(object sender, EventArgs e)
        {
            if (openFileDialogCsv.ShowDialog() == DialogResult.OK)
            {
                csvPath = openFileDialogCsv.FileName;
                string csvFileName = openFileDialogCsv.SafeFileName;

                if (!logic.CheckChosenCsvFile(csvPath))
                {
                    labelCsvError.Visible = true;
                    labelWavResult.Text = "";
                    csvOK = false;
                }
                else
                {
                    labelCsvError.Visible = false;
                    labelCsvResult.Text = csvFileName;
                    labelCsvResult.Visible = true;
                    csvOK = true;
                }

                if (csvOK && wavOK) buttonStart.Enabled = true;
                else buttonStart.Enabled = false;
            }
        }

        /// <summary>
        /// If button acts as start: search for hidden text in wav file
        /// If button acts as reset: initialize the form as before
        /// </summary>
        private void buttonStart_Click(object sender, EventArgs e)
        {
            if (resetKey)
            {
                // disable buttons
                buttonStart.Enabled = false;
                buttonSelectCsv.Enabled = false;
                buttonSelectWav.Enabled = false;

                // search for hidden text and display it
                int[] frequencies = logic.GetFrequencies(wavPath, csvPath);
                textBoxResult.Text = logic.GetSecretText(frequencies, csvPath);

                // enable reset
                resetKey = false;
                buttonStart.Enabled = true;
                buttonStart.Text = "Reset";
            }
            else // !resetKey
            {
                resetKey = true;
                buttonSelectCsv.Enabled = true;
                buttonSelectWav.Enabled = true;
                labelEventErrors.Visible = false;
                openFileDialogCsv.Reset();
                openFileDialogWav.Reset();
                textBoxResult.Text = "";
                buttonStart.Text = "Start";
                labelWavResult.Text = "";
                labelCsvResult.Text = "";
            }
        }
    }
}
