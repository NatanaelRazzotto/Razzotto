package com.razzotto;

import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import com.google.gson.Gson;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
    
     int QTDrowsArquivoAtual;
     int ContadorProgresso = 0;
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
	public  void LerCSV()
	{
		int contLinha =0;
		
		Gson gson = new Gson();
		List<String> Atributos = new ArrayList<String>();
		try {
			FileWriter writer = new FileWriter(dirDestinado, false);
			String row ;
		BufferedReader csvReader = new BufferedReader(new FileReader(dirOriginario));
			     	while (( row = csvReader.readLine()) != null) {
	    	    String[] arquivoMemoria = row.split(",");

				Pessoa informacoesPessoa = new Pessoa(arquivoMemoria[0], arquivoMemoria[1], arquivoMemoria[2],arquivoMemoria[3],arquivoMemoria[4], arquivoMemoria[5],
						arquivoMemoria[6],arquivoMemoria[7],arquivoMemoria[8],arquivoMemoria[9],arquivoMemoria[10],arquivoMemoria[11], arquivoMemoria[12],arquivoMemoria[13],arquivoMemoria[14],arquivoMemoria[15],arquivoMemoria[16],
						arquivoMemoria[17], arquivoMemoria[18],arquivoMemoria[19],arquivoMemoria[20],arquivoMemoria[21], arquivoMemoria[22],
						arquivoMemoria[23],arquivoMemoria[24]);
	     		 String json = gson.toJson(informacoesPessoa);
	     		 writer.write(json +"\n");
	     		 System.out.println(json);
	     		contLinha++;
     		}
	     	  writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void Calcula() {
		if ((dirOriginario != null)&&(dirDestinado!=null))
		{
			try {
				
				new Thread(TConversion).start();
				ContadorProgresso = 0;
				    new Thread(() -> {
				    	while (ContadorProgresso <= QTDrowsArquivoAtual) {
				    		 final int position = ContadorProgresso;
				    		 if (ContadorProgresso != QTDrowsArquivoAtual) {
				    		 Platform.runLater(() -> {
					            	PrB_Proecesso.setProgress((double)position/QTDrowsArquivoAtual);
					                System.out.println("Index: " + position);
					                //ContadorProgresso++;
					            });
				    		 }
				    		 else if (ContadorProgresso == QTDrowsArquivoAtual)
				    		 {
				    			 ContadorProgresso = 0;
				    			  try {
									Thread.sleep(9000);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
				    			 System.out.println("igual: " + position);
				    		 }
				    		  try{
					                Thread.sleep(500);
					            }catch(Exception e){ System.err.println(e); }
					         }
				    }).start();
				    	
			
			    /*	while (ContadorProgresso <= QTDrowsArquivoAtual) {
				PrB_Proecesso.setProgress(ContadorProgresso);
				System.out.println(ContadorProgresso);
				ContadorProgresso++;
					Thread.sleep(1000);
				} */
			    
			    
			System.out.println("-----");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else 
		{
			System.out.println("Não Foi encontrado o destino ");
			System.out.println(dirOriginario.getAbsolutePath());
			System.out.println(dirDestinado.getAbsolutePath());
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
	/*	while (ContadorProgresso <= QTDrowsArquivoAtual) {
			PrB_Proecesso.setProgress(ContadorProgresso);
			System.out.println(ContadorProgresso);
			ContadorProgresso++;
				Thread.sleep(1000);
			} */
			Thread.sleep(5000);
			
		}catch (InterruptedException e) {
			e.printStackTrace();
		}
	
	}
	public void ConverterCSVforGSON() {
		try {
			txtA_Status.appendText("*************Iniciando Leitura***************" + "\n");
			String row;
			//Gson gson = new Gson();
	//		FileWriter writer = new FileWriter(dirDestinado, false);
			List<Pessoa> ListaPessoas = new ArrayList<Pessoa>();
			BufferedReader csvReader = new BufferedReader(new FileReader(dirOriginario));
			BufferedReader contarLinhas = new BufferedReader(new FileReader(dirOriginario));
			while (( row = contarLinhas.readLine()) != null) {
				QTDrowsArquivoAtual++;
			}
			//PrB_Proecesso.setMaxWidth(QTDrowsArquivoAtual);
			txtA_Status.appendText("---------Quantidade de Linhas: " + QTDrowsArquivoAtual + "-----------\n");
			//PrB_Proecesso = new ProgressBar(QTDrowsArquivoAtual);
		//	System.out.println("tamanho: " + QTDrowsArquivoAtual);
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
			txtA_Status.appendText("-------------Leitura bem sucedida!------------" + "\n");
		//	System.out.println("Leitura Concluida Com Sucesso");
			EscreveJson(ListaPessoas);
	  
		} catch (Exception e) {
			txtA_Status.appendText("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + "\n");
			txtA_Status.appendText("A Leitura NÃO foi Concluida Com Sucesso! O tipo de arquivo pode não condiz com o tipo requerido" + "\n");
			txtA_Status.appendText("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + "\n");
			//System.out.println("A Leitura NÃO foi Concluida Com Sucesso! O tipo de arquivo pode não condizer com o tipo");
		}
	}
	public void EscreveJson (List<Pessoa> ListaPessoas) throws InterruptedException {
		try {
			Thread.sleep(5000);
			txtA_Status.appendText("*************Iniciando Conversão CSV >>> JSON***************" + "\n");
			FileWriter writer = new FileWriter(dirDestinado, false);
			Gson gson = new Gson();
			for (Pessoa pessoa : ListaPessoas) {
				 String json = gson.toJson(pessoa);
	     		 writer.write(json +"\n");
	     		 System.out.println(json);
			}
		   	writer.close();
			txtA_Status.appendText("*************Operação Finalizada com SUCESSO!***************" + "\n");
			} catch (IOException e) {
				txtA_Status.appendText("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + "\n");
				txtA_Status.appendText("Ocorreu um erro durente a Conversão" + "\n");
				txtA_Status.appendText("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + "\n");
				
		}

	}
    
}
