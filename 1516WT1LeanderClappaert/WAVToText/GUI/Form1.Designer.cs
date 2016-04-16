namespace GUI
{
    partial class Form1
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.labelLoadWav = new System.Windows.Forms.Label();
            this.openFileDialogWav = new System.Windows.Forms.OpenFileDialog();
            this.labelWavResult = new System.Windows.Forms.Label();
            this.buttonSelectWav = new System.Windows.Forms.Button();
            this.labelWavError = new System.Windows.Forms.Label();
            this.openFileDialogCsv = new System.Windows.Forms.OpenFileDialog();
            this.buttonSelectCsv = new System.Windows.Forms.Button();
            this.labelCsvResult = new System.Windows.Forms.Label();
            this.labelCsvError = new System.Windows.Forms.Label();
            this.textBoxResult = new System.Windows.Forms.TextBox();
            this.buttonStart = new System.Windows.Forms.Button();
            this.labelEventErrors = new System.Windows.Forms.Label();
            this.SuspendLayout();
            // 
            // labelLoadWav
            // 
            this.labelLoadWav.AutoSize = true;
            this.labelLoadWav.Location = new System.Drawing.Point(12, 9);
            this.labelLoadWav.Name = "labelLoadWav";
            this.labelLoadWav.Size = new System.Drawing.Size(130, 13);
            this.labelLoadWav.TabIndex = 0;
            this.labelLoadWav.Text = "Laad een wav bestand in:";
            // 
            // openFileDialogWav
            // 
            this.openFileDialogWav.FileName = "openFileDialogWav";
            // 
            // labelWavResult
            // 
            this.labelWavResult.AutoSize = true;
            this.labelWavResult.Location = new System.Drawing.Point(107, 39);
            this.labelWavResult.Name = "labelWavResult";
            this.labelWavResult.Size = new System.Drawing.Size(66, 13);
            this.labelWavResult.TabIndex = 2;
            this.labelWavResult.Text = "Gekozen file";
            // 
            // buttonSelectWav
            // 
            this.buttonSelectWav.Location = new System.Drawing.Point(15, 34);
            this.buttonSelectWav.Name = "buttonSelectWav";
            this.buttonSelectWav.Size = new System.Drawing.Size(75, 23);
            this.buttonSelectWav.TabIndex = 3;
            this.buttonSelectWav.Text = "Select wav";
            this.buttonSelectWav.UseVisualStyleBackColor = true;
            this.buttonSelectWav.Click += new System.EventHandler(this.buttonSelectWav_Click);
            // 
            // labelWavError
            // 
            this.labelWavError.AutoSize = true;
            this.labelWavError.ForeColor = System.Drawing.Color.Red;
            this.labelWavError.Location = new System.Drawing.Point(209, 39);
            this.labelWavError.Name = "labelWavError";
            this.labelWavError.Size = new System.Drawing.Size(189, 13);
            this.labelWavError.TabIndex = 4;
            this.labelWavError.Text = "Gelieve een geldige wav file te kiezen!";
            // 
            // openFileDialogCsv
            // 
            this.openFileDialogCsv.FileName = "openFileDialogCsv";
            // 
            // buttonSelectCsv
            // 
            this.buttonSelectCsv.Location = new System.Drawing.Point(15, 63);
            this.buttonSelectCsv.Name = "buttonSelectCsv";
            this.buttonSelectCsv.Size = new System.Drawing.Size(75, 23);
            this.buttonSelectCsv.TabIndex = 5;
            this.buttonSelectCsv.Text = "Select csv";
            this.buttonSelectCsv.UseVisualStyleBackColor = true;
            this.buttonSelectCsv.Click += new System.EventHandler(this.buttonSelectCsv_Click);
            // 
            // labelCsvResult
            // 
            this.labelCsvResult.AutoSize = true;
            this.labelCsvResult.Location = new System.Drawing.Point(107, 68);
            this.labelCsvResult.Name = "labelCsvResult";
            this.labelCsvResult.Size = new System.Drawing.Size(66, 13);
            this.labelCsvResult.TabIndex = 6;
            this.labelCsvResult.Text = "Gekozen file";
            // 
            // labelCsvError
            // 
            this.labelCsvError.AutoSize = true;
            this.labelCsvError.ForeColor = System.Drawing.Color.Red;
            this.labelCsvError.Location = new System.Drawing.Point(209, 68);
            this.labelCsvError.Name = "labelCsvError";
            this.labelCsvError.Size = new System.Drawing.Size(186, 13);
            this.labelCsvError.TabIndex = 7;
            this.labelCsvError.Text = "Gelieve een geldige csv file te kiezen!";
            // 
            // textBoxResult
            // 
            this.textBoxResult.Location = new System.Drawing.Point(15, 140);
            this.textBoxResult.Multiline = true;
            this.textBoxResult.Name = "textBoxResult";
            this.textBoxResult.ReadOnly = true;
            this.textBoxResult.Size = new System.Drawing.Size(380, 69);
            this.textBoxResult.TabIndex = 8;
            // 
            // buttonStart
            // 
            this.buttonStart.Location = new System.Drawing.Point(15, 102);
            this.buttonStart.Name = "buttonStart";
            this.buttonStart.Size = new System.Drawing.Size(75, 23);
            this.buttonStart.TabIndex = 9;
            this.buttonStart.Text = "Start";
            this.buttonStart.UseVisualStyleBackColor = true;
            this.buttonStart.Click += new System.EventHandler(this.buttonStart_Click);
            // 
            // labelEventErrors
            // 
            this.labelEventErrors.AutoSize = true;
            this.labelEventErrors.ForeColor = System.Drawing.Color.Red;
            this.labelEventErrors.Location = new System.Drawing.Point(107, 107);
            this.labelEventErrors.Name = "labelEventErrors";
            this.labelEventErrors.Size = new System.Drawing.Size(64, 13);
            this.labelEventErrors.TabIndex = 10;
            this.labelEventErrors.Text = "Event errors";
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(429, 237);
            this.Controls.Add(this.labelEventErrors);
            this.Controls.Add(this.buttonStart);
            this.Controls.Add(this.textBoxResult);
            this.Controls.Add(this.labelCsvError);
            this.Controls.Add(this.labelCsvResult);
            this.Controls.Add(this.buttonSelectCsv);
            this.Controls.Add(this.labelWavError);
            this.Controls.Add(this.buttonSelectWav);
            this.Controls.Add(this.labelWavResult);
            this.Controls.Add(this.labelLoadWav);
            this.Name = "Form1";
            this.Text = "Convert wav file to text";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label labelLoadWav;
        private System.Windows.Forms.OpenFileDialog openFileDialogWav;
        private System.Windows.Forms.Label labelWavResult;
        private System.Windows.Forms.Button buttonSelectWav;
        private System.Windows.Forms.Label labelWavError;
        private System.Windows.Forms.OpenFileDialog openFileDialogCsv;
        private System.Windows.Forms.Button buttonSelectCsv;
        private System.Windows.Forms.Label labelCsvResult;
        private System.Windows.Forms.Label labelCsvError;
        private System.Windows.Forms.TextBox textBoxResult;
        private System.Windows.Forms.Button buttonStart;
        private System.Windows.Forms.Label labelEventErrors;
    }
}

