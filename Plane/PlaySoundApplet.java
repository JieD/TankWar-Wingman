/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wingman;

import java.applet.Applet;
import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.sound.sampled.*;

public class PlaySoundApplet extends Applet implements ActionListener{
  Button play, exp, stop;
  AudioClip audioClip;
   AudioClip audioClip2;

  public void init(){
  play = new Button("  Play in Loop  ");
  add(play);
  play.addActionListener(this);
  
  exp = new Button("  Play Explosion  ");
  add(exp);
  exp.addActionListener(this);
  
  stop = new Button("  Stop  ");
  add(stop);
  stop.addActionListener(this);
  
  PlaySoundApplet aa = new PlaySoundApplet();
  //audioClip = getAudioClip(getCodeBase(), "background.wav");
  URL snd = aa.getClass().getResource("background.wav");
  audioClip= newAudioClip(snd);
  
 
  snd = aa.getClass().getResource("snd_explosion1.wav");
  audioClip2= newAudioClip(snd);
  }
  
  public void actionPerformed(ActionEvent ae){
  Button source = (Button)ae.getSource();
  if (source.getLabel() == "  Play in Loop  "){
  audioClip.loop();
  }
  else if (source.getLabel() == "  Play Explosion  "){
      audioClip2.play();
  
  }
  else if(source.getLabel() == "  Stop  "){
  audioClip.stop();
  }
  }
}
