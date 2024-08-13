//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            FileReader archivoFisico = new FileReader("startup-profit.csv");
            BufferedReader archivoLogico = new BufferedReader(archivoFisico);

            String registro;
            String[] campos;

            // Variables para almacenar sumas necesarias para el calculo
            double sumaProfit = 0.0;
            double sumaRD = 0.0;
            double sumaMarketing = 0.0;
            double sumaProfitRD = 0.0;
            double sumaProfitMarketing = 0.0;
            double sumaRDCuadrado = 0.0;
            double sumaMarketingCuadrado = 0.0;
            double sumaProfitCuadrado = 0.0;

            int contador = 0;

            // Leer la primera linea (encabezado) y descartarla
            archivoLogico.readLine();

            while ((registro = archivoLogico.readLine()) != null) {
                campos = registro.split(",");

                // Extraer los valores de interes
                double RDSpend = Double.parseDouble(campos[0]);
                double MarketingSpend = Double.parseDouble(campos[2]);
                double profit = Double.parseDouble(campos[4]);

                // Sumas necesarias para el calculo de correlacion
                sumaProfit += profit;
                sumaRD += RDSpend;
                sumaMarketing += MarketingSpend;

                sumaProfitRD += profit * RDSpend;
                sumaProfitMarketing += profit * MarketingSpend;

                sumaRDCuadrado += RDSpend * RDSpend;
                sumaMarketingCuadrado += MarketingSpend * MarketingSpend;
                sumaProfitCuadrado += profit * profit;

                contador++;
            }

            // Calcular coeficiente de correlacion de Pearson para Profit y R&D Spend
            double correlacionProfitRAndD = (contador * sumaProfitRD - sumaProfit * sumaRD) /
                    Math.sqrt((contador * sumaProfitCuadrado - sumaProfit * sumaProfit) *
                             (contador * sumaRDCuadrado - sumaRD * sumaRD));

            // Calcular coeficiente de correlacion de Pearson para Profit y Marketing Spend
            double correlacionProfitMarketing = (contador * sumaProfitMarketing - sumaProfit * sumaMarketing) /
                    Math.sqrt((contador * sumaProfitCuadrado - sumaProfit * sumaProfit) *
                             (contador * sumaMarketingCuadrado - sumaMarketing * sumaMarketing));

            // Imprimir resultados
            System.out.println("Coeficiente de correlacion entre Profit y R&D Spend: " + correlacionProfitRAndD);
            System.out.println("Coeficiente de correlacion entre Profit y Marketing Spend: " + correlacionProfitMarketing);

            // Cerrar archivos
            archivoLogico.close();
            archivoFisico.close();

        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}