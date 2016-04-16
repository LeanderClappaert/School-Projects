using System;
using System.Windows.Forms;
using LogicImplementation;

namespace GUI
{
    /// <summary>
    /// Generates GUI for user input.
    /// </summary>
    public partial class TextToWavGUI : Form
    {
        //private variable(s).
        private Logic logic;
        private DialogResult result;

        /// <summary>
        /// Setup the GUI for first time.
        /// </summary>
        public TextToWavGUI()
        {
            InitializeComponent();
            this.logic = new Logic();
            labelError.Visible = false;
            labelOK.Visible = false;
            buttonGenerate.Visible = false;
        }

        /// <summary>
        /// Generates audiofile.
        /// </summary>
        private void button1_Click(object sender, EventArgs e)
        {
            logic.makeWavFile();
            buttonGenerate.Visible = false;
            labelOK.Visible = true;
        }

        /// <summary>
        /// User can choose where they want to store the file.
        /// </summary>
        private void buttonPath_Click(object sender, EventArgs e)
        {
            //edit default folder properties.
            folderBrowserDialog1.Description = "Selecteer de map waar u het audiobestand wilt opslaan.";
            folderBrowserDialog1.RootFolder = Environment.SpecialFolder.Personal; //use the My Documents folder.

            //if open button is clicked, user can generate their text.
            result = folderBrowserDialog1.ShowDialog();

            if ((result == DialogResult.OK))
            {
                string path = folderBrowserDialog1.SelectedPath;
                logic.Path = path;
            }
        }

        /// <summary>
        /// Check if OK button was clicked and if generate button can be active.
        /// </summary>
        private void buttonOK_Click(object sender, EventArgs e)
        {
            string filename = textBox1.Text;
            string input = textBox.Text;

            //Check if path has been chosen, check if filename and textinput are correct.
            if ((result == DialogResult.OK) && (logic.CheckFileName(filename)) && (logic.CheckTextInput(input)))
            {
                logic.Filename = filename;
                buttonGenerate.Visible = true;
                logic.InputText = input;
                labelError.Visible = false;
                labelOK.Visible = false;
            }
            else
            {
                labelError.Text = "Gelieve geldige characters in te geven!";
                labelError.Visible = true;
                labelOK.Visible = false;
                buttonGenerate.Visible = false;
            }
        }

        /// <summary>
        /// These properties are empty.
        /// </summary>
        private void label1_Click(object sender, EventArgs e)
        {

        }
        private void label3_Click(object sender, EventArgs e)
        {

        }
        private void label4_Click(object sender, EventArgs e)
        {

        }
        private void textBox1_TextChanged(object sender, EventArgs e)
        {

        }
        private void label2_Click(object sender, EventArgs e)
        {

        }
        private void textBox_TextChanged(object sender, EventArgs e)
        {

        }
    }
}
