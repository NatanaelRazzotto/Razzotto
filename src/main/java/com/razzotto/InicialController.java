package com.razzotto;

import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import com.google.gson.Gson;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;

public class InicialController {
    @FXML
    private Button btn_AbrirArquivo;
    @FXML
    private Button btn_SalvarArquivo;
    @FXML
    private Button btn_ConverteArquivo;
    @FXML
    private ProgressBar PrB_Proecesso;
    @FXML 
    private TextArea txtA_Status;
    @FXML 
    private TextArea txtA_Progress;
    
     Vector<String>ContabilidadeTempo = new Vector<String>();
     String MensagemStatus= "operação";
     int QTDrowsArquivoAtual = 100;
     int ContadorProgresso = 0;
     int ContadorProgressoa = 0;
     Boolean ControleStatus = true;
     File dirOriginario ;
     File dirDestinado;
    
    @FXML////////////////////////////////////////////////////////////
    private void AbrirArquivo(ActionEvent event) {
    	try {
    		System.out.println(Captura_Arquivo());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

    }
    @FXML//////////////////////////////////////////
    private void SalvarArquivo(ActionEvent event) {
    	try {
    		Salva_ArquivoJSON();
		} catch (Exception e) {
			e.printStackTrace();
		}

    }
    @FXML
    private void ConverterCSV(ActionEvent event) {
    	try {		
		
			if ((dirOriginario == null)&&(dirDestinado==null)) {
				btn_ConverteArquivo.setDisable(true);
    		Captura_Arquivo();
    		Salva_Arquivo();
    		txtA_Status.appendText("----------BEM VINDO-----------" + "\n");
    		txtA_Status.appendText(dirOriginario + "\n");
    		txtA_Status.appendText(dirDestinado + "\n");
   			System.out.println(dirOriginario);
			System.out.println(dirDestinado);
    		Calcula();
    		}
	        
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
    public File Captura_Arquivo() throws FileNotFoundException {
    	JFileChooser file_chooser = new JFileChooser();
    	StringBuilder sb = new StringBuilder();
    	
    	if(file_chooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION)
    	{
    		File file = file_chooser.getSelectedFile();
    		dirOriginario = file;
    	}
		return null;
    }
    public File Salva_Arquivo() {
    	JFrame parentFrame = new JFrame();
    	JFileChooser file_chooser = new JFileChooser();
    	file_chooser.setDialogTitle("Onde Deseja Salvar o Arquivo CSV");
    	int userSelection = file_chooser.showSaveDialog(parentFrame);
    	if (userSelection == JFileChooser.APPROVE_OPTION) {
    	    File fileToSave = file_chooser.getSelectedFile();
    	    dirDestinado = fileToSave;
    	    return fileToSave;
    	}
		return null;
    	
    }
    public void Salva_ArquivoJSON() {
    	JFrame parentFrame = new JFrame();
    	JFileChooser file_chooser = new JFileChooser();
    	file_chooser.setDialogTitle("Onde Deseja Salvar o Arquivo");
    	int userSelection = file_chooser.showSaveDialog(parentFrame);
    	if (userSelection == JFileChooser.APPROVE_OPTION) {
    	    File fileToSave = file_chooser.getSelectedFile();
    	    gerarVeiculos(fileToSave);
      	    System.out.println("Save as file: " + fileToSave.getAbsolutePath());
    	}
    	
    }
    public static void gerarVeiculos(File dir)
	{
    	  
		System.out.println("sdadsad");
		List<String> Modelo = Arrays.asList("Onix","Palio","Fiesta","Argo","HB20");
		List<String> alfabeto = Arrays.asList("A","B","C","D","F","G","H","I","J","K","L",
				"M","N","O","P","Q","R","S","T","U","V","W","Y","X","Z");
		try {
			System.out.println("gfhghgh");
		Random gerador = new Random();
		FileWriter csvWriter = new FileWriter(dir, false);
			for (int i = 0; i < 50000; i ++)
			{
				csvWriter.append(String.join(",", alfabeto.get(gerador.nextInt(24)) + alfabeto.get(gerador.nextInt(24))
						+alfabeto.get(gerador.nextInt(24))+"-"+gerador.nextInt(10)+gerador.nextInt(10)+gerador.nextInt(10)+gerador.nextInt(10)));
				csvWriter.append(String.join(",","," +Modelo.get(gerador.nextInt(4))));
				csvWriter.append("\n");
			}
			csvWriter.flush();
			csvWriter.close();
			System.out.println("deu");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     
	}
	public void Calcula() {
		if ((dirOriginario != null)&&(dirDestinado!=null))
		{
			try {
				/*new Thread(() -> {
					Platform.runLater(() -> {
					ConverterCSVforGSON();
					 });
				}).start();*/
		/*		new Thread(() -> {
					while(ControleStatus) {
						try {
					    Platform.runLater(() -> {
						txtA_Status.appendText(MensagemStatus);
						MensagemStatus = "";
						});
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							System.out.println("Problemas nas Mensagems");
							//e.printStackTrace();
						}
					}
				}).start();*/
				//new Thread(TConversion).start();
			//	QTDrowsArquivoAtual = 100;
				//ContadorProgressoa = 0;
				new Thread(() -> {
				    	System.out.println("Iniciando Operações");
				    	while (ContadorProgresso <= QTDrowsArquivoAtual) {
				    		 final int position = ContadorProgresso;
				    		 if (ContadorProgresso != QTDrowsArquivoAtual) {
				    		 Platform.runLater(() -> {
					            	PrB_Proecesso.setProgress((double)position/100);
					               System.out.println("Index: " + position);
					            });
				    		 }
				    		 else if (ContadorProgresso == QTDrowsArquivoAtual)
				    		 {
				    			ContadorProgresso++;
  			 
				    			  try {
									Thread.sleep(9000);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
				    		 }
				    		  try{
				    			  Thread.sleep(500);
					            }catch(Exception e){ System.err.println(e); }
					         }
				    }).start();
				////////
				ConverterCSVforGSON();
				txtA_Status.appendText("Resultado da Contabilização de TEMPO:" + "\n");
				for (String Tempo : ContabilidadeTempo) {
					txtA_Status.appendText(Tempo);
					txtA_Status.appendText("\n");
				}
				    				    
			System.out.println("-----");
			} catch (Exception e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("OCORREU UM ERRO!");
				alert.setHeaderText(null);
				alert.setContentText("Não Foi Possivel Converter!");

				alert.showAndWait();
			}
		}
		else 
		{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("INFORME DIRETORIO");
			alert.setHeaderText(null);
			alert.setContentText("Deve-se Informar os Diretorios!");

			alert.showAndWait();
			
		}
		
//
	
	
	//	
	}
	/*private Runnable TProgress = new Runnable() {
		@Override
		public void run() {
			try {
				atualizaProgress();
				System.out.println("deu certo PREGRESS");
			} catch (Exception e) {
				System.out.println("nÃO FOI POSSIVEL PROGRESS");
			}
			
		}
	};*/
	private Runnable TConversion = new Runnable() {
		@Override
		public void run() {
			try {
				ConverterCSVforGSON();
				System.out.println("deu certo conversao");
				
			} catch (Exception e) {
				System.out.println("nÃO FOI POSSIVEL");
			}
			
		}
	};
	public void atualizaProgress()  {//
		try {
			PrB_Proecesso.setMaxWidth(1000);
			Thread.sleep(5000);
			PrB_Proecesso.setProgress(500);
			Thread.sleep(5000);
			
		}catch (InterruptedException e) {
			e.printStackTrace();
		}
	
	}
	public String obterDuracao(Instant inicio, Instant fim, String Mensagem) {
		Duration decorrido = Duration.between(inicio, fim);
		long decorridoMilissegundos = decorrido.toMillis();
		String Retorno = Mensagem + decorridoMilissegundos + " Milesgundos";
		return Retorno;
		
	}
	public void ConverterCSVforGSON() {
		try {
			MensagemStatus="*************Iniciando Leitura***************" + "\n";
			String row;
			List<Pessoa> ListaPessoas = new ArrayList<Pessoa>();
			Instant inicioOpenFile =Instant.now();
			BufferedReader csvReader = new BufferedReader(new FileReader(dirOriginario));
			Instant fimOpenFile = Instant.now();
			ContabilidadeTempo.add(obterDuracao(inicioOpenFile, fimOpenFile, "Tempo para abrir Arquivo:"));
			BufferedReader contarLinhas = new BufferedReader(new FileReader(dirOriginario));
			while (( row = contarLinhas.readLine()) != null) {
				QTDrowsArquivoAtual++;
			}
			MensagemStatus="---------Quantidade de Linhas: " + QTDrowsArquivoAtual + "-----------\n";
			Instant inicioLeituraFile =Instant.now();
			while (( row = csvReader.readLine()) != null) {
				ContadorProgresso++;
				//Thread.sleep(5000);
	    	    String[] arquivoMemoria = row.split(",");
	    	    Pessoa informacoesPessoa = new Pessoa(arquivoMemoria[0], arquivoMemoria[1], arquivoMemoria[2],arquivoMemoria[3],arquivoMemoria[4], arquivoMemoria[5],
						arquivoMemoria[6],arquivoMemoria[7],arquivoMemoria[8],arquivoMemoria[9],arquivoMemoria[10],arquivoMemoria[11], arquivoMemoria[12],arquivoMemoria[13],arquivoMemoria[14],arquivoMemoria[15],arquivoMemoria[16],
						arquivoMemoria[17], arquivoMemoria[18],arquivoMemoria[19],arquivoMemoria[20],arquivoMemoria[21], arquivoMemoria[22],
						arquivoMemoria[23],arquivoMemoria[24]);
	    	     ListaPessoas.add(informacoesPessoa);
   		 
     		}
			Instant fimLeituraFile = Instant.now();
			ContabilidadeTempo.add(obterDuracao(inicioLeituraFile, fimLeituraFile, "Tempo leitura:"));
			MensagemStatus="-------------Leitura bem sucedida!------------" + "\n";
			EscreveJson(ListaPessoas);
	  
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("OCORREU UM ERRO!");
			alert.setHeaderText(null);
			alert.setContentText("A Leitura NÃO foi Concluida Com Sucesso! \n O tipo de arquivo pode não condiz com o tipo requerido");
			alert.showAndWait();
		
		}
	}
	public void EscreveJson (List<Pessoa> ListaPessoas) throws InterruptedException {
		try {
			List<String> ObjJson = new ArrayList<String>();
			MensagemStatus="*************Iniciando Conversão CSV >>> JSON***************" + "\n";
			FileWriter writer = new FileWriter(dirDestinado, false);
			Gson gson = new Gson();
			Instant InicioConversao = Instant.now();
			for (Pessoa pessoa : ListaPessoas) {
				 ContadorProgresso++;
				 String json = gson.toJson(pessoa);
				 ObjJson.add(json);
	     		 writer.write(json +"\n");
			}
			Instant FimConversao = Instant.now();
			ContabilidadeTempo.add(obterDuracao(InicioConversao, FimConversao, "Tempo Conversão:"));
			Instant InicioEscrita = Instant.now();
			for (String pessoa : ObjJson) {
				 ContadorProgresso++;
	     		 writer.write(pessoa +"\n");
			}
		   	writer.close();
			Instant FimEscrita = Instant.now();
			ContabilidadeTempo.add(obterDuracao(InicioEscrita, FimEscrita, "Tempo Escrita:"));
		   	MensagemStatus="*************Operação Finalizada com SUCESSO!***************" + "\n";
			} catch (IOException e) {
				
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("OCORREU UM ERRO!");
				alert.setHeaderText(null);
				alert.setContentText("Ocorreu um erro durente a Conversão");
				alert.showAndWait();
				
		}

	}
    
}
