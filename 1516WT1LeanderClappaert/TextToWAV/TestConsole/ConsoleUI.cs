using System;
using LogicImplementation;
using System.Media;

namespace TestConsole
{
    class ConsoleUI
    {
        public void start()
        {
            Logic x = new Logic();

            x.InputText = "Leander is de max!";
            int[] fr = x.InputFrequencies;
            x.makeWavFile();
            Console.ReadLine();

            SoundPlayer player = new SoundPlayer(@"a.wav");
            player.Load();
            player.Play();
            Console.WriteLine("playing");
            //System.Threading.Thread.Sleep(2000); //http://stackoverflow.com/questions/11836159/soundplayer-not-playing-any-bundled-windows-sounds-pcm-wav-files
            Console.ReadLine();
        }
        static void Main(string[] args)
        {
            ConsoleUI x = new ConsoleUI();
            x.start();
        }
    }
}
