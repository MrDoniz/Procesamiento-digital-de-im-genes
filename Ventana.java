
//import java.io.*;
import java.io.File;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
//import java.io.FileInputStream;
import javax.swing.JFileChooser;
//import java.io.FileOutputStream;
//import org.apache.commons.io.FileUtils;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

public class Ventana {
  private String ruta;

  public void Abririmg(JFrame ventana, int x, int y, int z, int w, String ruta) {
    try {
      JLabel imagen=new JLabel();
      imagen.setIcon(new ImageIcon(ImageIO.read(new File(ruta))));
      imagen.setBounds(x, y, z, w);
      ventana.add(imagen);
   } catch (IOException error) {
      error.printStackTrace();
   }
  } 

  public void Boton(JFrame ventana, int x, int y, int anchura, int altura, String mensaje, int indice) {
    JButton bt = new JButton(mensaje);
    if (indice == 0) {
      bt.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent arg0) {
          System.out.println(mensaje);
          JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
          jfc.setDialogTitle("Selecciona una imagen");
          jfc.setAcceptAllFileFilterUsed(false);
          FileNameExtensionFilter filter = new FileNameExtensionFilter("Formato TIFF, BMP, PNG, JPEG", "tif", "tiff", "bmp", "png", "jpeg");
          jfc.addChoosableFileFilter(filter);
          
          int returnValue = jfc.showOpenDialog(null);
          if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            ruta = selectedFile.getAbsolutePath();
            System.out.println(ruta);
            Abririmg(ventana, 10, 10, 500, 500, ruta);
          }
        }
      });
    } else if (indice == 1) {
      bt.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent arg0) {
            System.out.println(mensaje);
            Abririmg(ventana, 650, 10, 500, 500, ruta);
        }
      });
    } else if (indice == 2) {
      bt.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent arg0) {
            System.out.println(mensaje);
            Clonar();
        }
      });
    } 
    bt.setBounds(x, y, anchura, altura); 
    ventana.add(bt);
  }

  public void Clonar(){
    // "F:\Mi unidad\Universidad de La Laguna\4?? A??o\VPC\Pr??ctica 1\copiado.tff"
    // "F:/Mi unidad/Universidad de La Laguna/4?? A??o/VPC/Pr??ctica 1/copiado.tff"
  }

  Ventana() {
    JFrame f = new JFrame("Daniel Doniz Garcia"); 
    f.setSize(1180,610); 
    f.setLayout(null); 
    f.setVisible(true);

    Boton(f, 200, 530, 120, 30, "Abrir imagen", 0);
    Boton(f, 850, 530, 120, 30, "Guardar imagen", 1);

    Boton(f, 530, 30, 100, 40, "Monocromo", 2);
    Boton(f, 530, 80, 100, 40, "Monocromo", 2);
  }
  public static void main(String[] args) { 
    new Ventana(); 
  }
}


/*
Lineal, pixel a pixel.
  Color Vin -> Color Vout
  Vout = 3,5 * Vin - 9
No lineal, 
  Vout = log(Vin)

La estructura de una imagen en color son 3 matrices, en una posicion de 1 matriz est?? la informaci??n
  localizaci??n
  RGB, esa posicion, pixel tiene su homologo en las otras 2 matrices, en la array 2?? est?? la G y en la 3?? B
  Puede que haya una cuarta matriz, ypara cada pixel se asocian 4 valores en vez de 3, el 4 tiene el canal alfa que es 
  la opacidad o transparencia
  Ese ultiimo ni lo tocamos, lo dejamos como est??

Imagenes en color
  lo normal son 32 bits, rgb 24 y el restante para el alfa
  cada color se codifica con 8 bits, da posibilidad de 256 de rojo (verde o azul) por matriz

  IMPORTANTE!
  EL VA A CORREGIRLO TODO EN MONOCROMO. PONER TODO LO DE COLOR CON UN BOT??N ADICIONAL
  LA APLICACI??N DEBE TENER LA OPCI??N PARA CAMBIAR LA IMAGEN EN COLOR A MONOCROMO (escala de grises)
    y ese valor estar?? entre 0 y 255
    PAL sistema de televisi??n
    NTSC estandar de televisi??n en estados unidos
    para en el sistema pal pasar de color a monocromo
    multiplicando con esos valores, combino los valores rgb multiplicandolos para juntarlos
    GRIS = 0.299 R + 0.587 G + 0.114 B
    guardo esos valores en un array
    NO CAMBIO LA ESTRUCTURA
    los rgb, pongo en los 3 array elmismo numero
    calculo el GRIS y pongo el n??mero resultante en las 3 matrices.

  Uso de tablas de transformaci??n
    matriz vin, posicion 1 vcalculo vout = 3.8* Vin
    y calculo la posicion 1 de matriz vout, despu??s la posici??n 2... 3... 
    no repetir la operaci??n todo el rato
    hacer una tabla de transformaci??n
    donde vin | vout
          0     332
          1     243
  ROI
    con el raton poder selecionar una regi??n y aplicar la modificaci??n a la matriz

  Boton de informaci??n de la imagen.
    Tipo de fichero: bmp, tiff, ...
    ??? Histograma: Posibilidad de visualizar tanto el histograma de valores absolutos
    como el histograma acumulativo, as?? como poder obtener informaci??n detallada
    del mismo para cada nivel de gris.
    ??? Tama??o de la imagen: expresado en filas x columnas (?? columnas x filas)
    ??? Rango de valores: Se especificar?? el intervalo [min, max] correspondiente a los
    valores m??nimo y m??ximo del rango de grises detectados en la imagen.
    ??? Brillo y contraste: Estos par??metros se estiman num??ricamente a partir de la
    media y desviaci??n t??pica de los niveles de gris de todos los pixels de la imagen
    ( lo normal es obtenerlos a partir del histograma).
    ??? Entrop??a
    ??? Posici??n y nivel de gris: Para cada posici??n indicada por el rat??n al movernos
    dentro de la imagen se deber??n indicar las coordenadas de cada p??xel junto a
    su nivel de gris y/o color.
  
    Histograma 
      h_I (51) = numero de pixels que hay en la imagen con color 51
      H_I(i)   = sumariotio_k=0 ^i  H_I(k) H_I(51) (acumulativo)
      normalizar los dos histogramas
        Que en los dos histogramas solo salganvalores entre 0 y 1,se hacediviendo 1/ el tama??o de la imagen. 
      Si tengo una imagen en color, lo hago para los 3.
      
      Tengo que visualizar los 2 normales y poder tener la opci??n de la versi??n normalizada.
      Usar una libreria para esto
    
    Rango de valores
      en esta imagen el color mas oscuro que he encontrado es este y el valor mas claro.
      pixel con el valor menor.
      Que intervalo de colores estoy utilizando
      si es en color se lo doy por cada array.
      si es en gris solo 1
      de 0 a 255
      alomejor estoy usando de 100 a 160

    Brillo
      El brillo es la media de un conjunto de valores
        sumar para todos los pixel y divide entre el n??mero de pixeles
      Con el histograma ya se con 0 cuantos hay, con 1,con2..., con 255
        La suma la har?? para todos los colores 1/size * (sumatorio de 0 a 255 (h(i)i) )
      Si estoy trabajando con entre 0 y 255, la media me debe dar un valor entre esos 2 numeros

      Un bot??n con la opci??n de Con color, pondriamos el brillo en cada plano.

    Contraste
      si el rango de valores est??n entre 10 y 18, el contraste separa o junta mas los colores
      Es la desviaci??n t??pica

      raiz de (1/size * (sumatorio de 0 a 255 de(h(i)*(i- media)^2) ) )
      esto da un valor. el rango de valores con imagenes de 8 bits, puede haber tdoos del mismo color
      sigma 0 y 127
      solo puede dar entre esos dos valores
    
    Entropia (E)
      E = - (sumarotio de 1 = 0 a 255 de (p(i) * log_2 de(p(i)) ) )
      Siendo p(i) = h(i)/size
      h(i) es el histograma.
      Da valores entre 0 y 8.
      El 8 es el n??mero de bit que estoy codificando.
      n?? de bit minimo para codificar en la imagen.
      si da una entropia de  4,67 con 5 bits ya me dar??a.
      cuanto mayor es el numero, mas informaci??n tiene.
      entropia es la cantidad de informaci??n que tiene una imagen.
*/