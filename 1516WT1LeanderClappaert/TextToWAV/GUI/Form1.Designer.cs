namespace GUI
{
    partial class TextToWavGUI
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
            this.labelError = new System.Windows.Forms.Label();
            this.textBox = new System.Windows.Forms.TextBox();
            this.buttonGenerate = new System.Windows.Forms.Button();
            this.label2 = new System.Windows.Forms.Label();
            this.folderBrowserDialog1 = new System.Windows.Forms.FolderBrowserDialog();
            this.buttonPath = new System.Windows.Forms.Button();
            this.label1 = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.label4 = new System.Windows.Forms.Label();
            this.textBox1 = new System.Windows.Forms.TextBox();
            this.buttonOK = new System.Windows.Forms.Button();
            this.labelOK = new System.Windows.Forms.Label();
            this.SuspendLayout();
            // 
            // labelError
            // 
            this.labelError.AutoSize = true;
            this.labelError.Location = new System.Drawing.Point(252, 162);
            this.labelError.Name = "labelError";
            this.labelError.Size = new System.Drawing.Size(192, 13);
            this.labelError.TabIndex = 0;
            this.labelError.Text = "Gelieve geldige characters in te geven.";
            // 
            // textBox
            // 
            this.textBox.Location = new System.Drawing.Point(43, 84);
            this.textBox.MaximumSize = new System.Drawing.Size(401, 75);
            this.textBox.MaxLength = 240;
            this.textBox.MinimumSize = new System.Drawing.Size(401, 75);
            this.textBox.Multiline = true;
            this.textBox.Name = "textBox";
            this.textBox.Size = new System.Drawing.Size(401, 75);
            this.textBox.TabIndex = 1;
            this.textBox.TextChanged += new System.EventHandler(this.textBox_TextChanged);
            // 
            // buttonGenerate
            // 
            this.buttonGenerate.Location = new System.Drawing.Point(369, 216);
            this.buttonGenerate.Name = "buttonGenerate";
            this.buttonGenerate.Size = new System.Drawing.Size(75, 23);
            this.buttonGenerate.TabIndex = 2;
            this.buttonGenerate.Text = "Generate";
            this.buttonGenerate.UseVisualStyleBackColor = true;
            this.buttonGenerate.Click += new System.EventHandler(this.button1_Click);
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(40, 162);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(101, 13);
            this.label2.TabIndex = 3;
            this.label2.Text = "Max 240 characters";
            this.label2.Click += new System.EventHandler(this.label2_Click);
            // 
            // buttonPath
            // 
            this.buttonPath.Location = new System.Drawing.Point(43, 216);
            this.buttonPath.Name = "buttonPath";
            this.buttonPath.Size = new System.Drawing.Size(75, 23);
            this.buttonPath.TabIndex = 4;
            this.buttonPath.Text = "Path";
            this.buttonPath.UseVisualStyleBackColor = true;
            this.buttonPath.Click += new System.EventHandler(this.buttonPath_Click);
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(43, 9);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(203, 13);
            this.label1.TabIndex = 5;
            this.label1.Text = "Geef uw tekst in onderstaande textbox in.";
            this.label1.Click += new System.EventHandler(this.label1_Click);
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(43, 57);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(300, 13);
            this.label3.TabIndex = 6;
            this.label3.Text = "Klik op de generate knop om het audio bestand  te genereren.";
            this.label3.Click += new System.EventHandler(this.label3_Click);
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(43, 34);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(311, 13);
            this.label4.TabIndex = 7;
            this.label4.Text = "Kies jouw bestandslocatie via de path knop en kies uw filename.";
            this.label4.Click += new System.EventHandler(this.label4_Click);
            // 
            // textBox1
            // 
            this.textBox1.Location = new System.Drawing.Point(124, 219);
            this.textBox1.MaxLength = 8;
            this.textBox1.Name = "textBox1";
            this.textBox1.Size = new System.Drawing.Size(100, 20);
            this.textBox1.TabIndex = 8;
            this.textBox1.TextChanged += new System.EventHandler(this.textBox1_TextChanged);
            // 
            // buttonOK
            // 
            this.buttonOK.Location = new System.Drawing.Point(230, 217);
            this.buttonOK.Name = "buttonOK";
            this.buttonOK.Size = new System.Drawing.Size(34, 23);
            this.buttonOK.TabIndex = 9;
            this.buttonOK.Text = "OK";
            this.buttonOK.UseVisualStyleBackColor = true;
            this.buttonOK.Click += new System.EventHandler(this.buttonOK_Click);
            // 
            // labelOK
            // 
            this.labelOK.AutoSize = true;
            this.labelOK.Location = new System.Drawing.Point(316, 221);
            this.labelOK.Name = "labelOK";
            this.labelOK.Size = new System.Drawing.Size(156, 13);
            this.labelOK.TabIndex = 10;
            this.labelOK.Text = "Het bestand werd aangemaakt!";
            // 
            // TextToWavGUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(484, 261);
            this.Controls.Add(this.labelOK);
            this.Controls.Add(this.buttonOK);
            this.Controls.Add(this.textBox1);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.buttonPath);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.buttonGenerate);
            this.Controls.Add(this.textBox);
            this.Controls.Add(this.labelError);
            this.MaximumSize = new System.Drawing.Size(500, 300);
            this.MinimumSize = new System.Drawing.Size(500, 300);
            this.Name = "TextToWavGUI";
            this.Text = "TextToWav";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label labelError;
        private System.Windows.Forms.TextBox textBox;
        private System.Windows.Forms.Button buttonGenerate;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.FolderBrowserDialog folderBrowserDialog1;
        private System.Windows.Forms.Button buttonPath;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.TextBox textBox1;
        private System.Windows.Forms.Button buttonOK;
        private System.Windows.Forms.Label labelOK;
    }
}

