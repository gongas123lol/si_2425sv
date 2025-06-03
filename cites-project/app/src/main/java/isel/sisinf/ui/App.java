/*
MIT License

Copyright (c) 2025, Nuno Datia, Matilde Pato, ISEL

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
package isel.sisinf.ui;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.HashMap;
import java.io.InputStreamReader;

import isel.sisinf.Dal;
import isel.sisinf.model.Rider;
import isel.sisinf.repo.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;

import java.io.BufferedReader;

/**
 * 
 * Didactic material to support 
 * to the curricular unit of 
 * Introduction to Information Systems 
 *
 * The examples may not be complete and/or totally correct.
 * They are made available for teaching and learning purposes and 
 * any inaccuracies are the subject of debate.
 */

interface DbWorker
{
    void doWork();
}
class UI
{
    private enum Option
    {
        // DO NOT CHANGE ANYTHING!
        Unknown,
        Exit,
        createCostumer,
        listCostumer,
        listDocks,
        startTrip,
        parkScooter,
        about
    }
    private static UI __instance = null;
  
    private HashMap<Option,DbWorker> __dbMethods;

    private UI()
    {
        // DO NOT CHANGE ANYTHING!
        __dbMethods = new HashMap<Option,DbWorker>();
        __dbMethods.put(Option.createCostumer, () -> UI.this.createCostumer());
        __dbMethods.put(Option.listCostumer, () -> UI.this.listCostumer()); 
        __dbMethods.put(Option.listDocks, () -> UI.this.listDocks());
        __dbMethods.put(Option.startTrip, new DbWorker() {public void doWork() {UI.this.startTrip();}});
        __dbMethods.put(Option.parkScooter, new DbWorker() {public void doWork() {UI.this.parkScooter();}});
        __dbMethods.put(Option.about, new DbWorker() {public void doWork() {UI.this.about();}});
    }

    public static UI getInstance()
    {
        // DO NOT CHANGE ANYTHING!
        if(__instance == null)
        {
            __instance = new UI();
        }
        return __instance;
    }

    private Option DisplayMenu()
    {
        Option option = Option.Unknown;
        Scanner s = new Scanner(System.in); //Scanner closes System.in if you call close(). Don't do it
        try
        {
            // DO NOT CHANGE ANYTHING!
            System.out.println("CITES Manadgement DEMO");
            System.out.println();
            System.out.println("1. Exit");
            System.out.println("2. Create Costumer");
            System.out.println("3. List Existing Costumer");
            System.out.println("4. List Docks");
            System.out.println("5. Start Trip");
            System.out.println("6. Park Scooter");
            System.out.println("8. About");
            System.out.print(">");
            int result = s.nextInt();
            option = Option.values()[result];
        }
        catch(RuntimeException ex)
        {
            //nothing to do.
        }
        
        return option;

    }
    private static void clearConsole() throws Exception
    {
        // DO NOT CHANGE ANYTHING!
        for (int y = 0; y < 25; y++) //console is 80 columns and 25 lines
            System.out.println("\n");
    }

    public void Run() throws Exception
    {
        // DO NOT CHANGE ANYTHING!
        Option userInput;
        do
        {
            clearConsole();
            userInput = DisplayMenu();
            clearConsole();
            try
            {
                __dbMethods.get(userInput).doWork();
                System.in.read();
            }
            catch(NullPointerException ex)
            {
                //Nothing to do. The option was not a valid one. Read another.
            }

        }while(userInput!=Option.Exit);
    }

    /**
    To implement from this point forward. 
    -------------------------------------------------------------------------------------     
        IMPORTANT:
    --- DO NOT MESS WITH THE CODE ABOVE. YOU JUST HAVE TO IMPLEMENT THE METHODS BELOW ---
    --- Other Methods and properties can be added to support implementation. 
    ---- Do that also below                                                         -----
    -------------------------------------------------------------------------------------
    
    */

    private static final int TAB_SIZE = 24;

    private void createCostumer() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try (JPAContext ctx = new JPAContext()) {
            System.out.println("Nome?");
            String name = reader.readLine();
            System.out.println("Email?");
            String email = reader.readLine();
            System.out.println("NIF?");
            Integer taxnr = Integer.valueOf(reader.readLine());
            System.out.println("Tipo de cartão? (resident/tourist)");
            String typeOfCard = reader.readLine();

            Rider rider = new Rider();
            rider.setName(name);
            rider.setEmail(email);
            rider.setTaxNumber(taxnr);
            rider.setDtRegister(LocalDateTime.now());
            rider.setCredit(BigDecimal.ZERO);
            rider.setTypeOfCardDb(typeOfCard);

            ctx.beginTransaction();
            ctx.getRiders().save(rider);
            ctx.commit();

            System.out.println("Cliente criado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao criar cliente: " + e.getMessage());
        }
    }


    private void listCostumer() {
        try (JPAContext ctx = new JPAContext()) {

            var riders = ctx.getRiders().findAll();
            System.out.println(riders.get(0).getName());
            if (riders.isEmpty()) {
                System.out.println("Não existem utilizadores registados.");
            } else {
                System.out.println("Id | Nome | Email | NIF | Data Registo | ID Cartão | Crédito");
                for (var r : riders) {
                    System.out.println(
                            r.getId()        + " | " +
                            r.getName()      + " | " +
                            r.getEmail()     + " | " +
                            r.getTaxNumber() + " | " +
                            r.getDtRegister()+ " | " +
                            r.getCard().getId()+ " | " +
                            r.getCredit()
                    );
                }
            }

        } catch (Exception ex) {
            System.out.println("Erro ao listar utilizadores: " + ex.getMessage());
        } finally {
            System.out.println("\nPrima ENTER para continuar...");
            new Scanner(System.in).nextLine();
        }
    }

    private void listDocks() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        EntityManagerFactory emf = null;
        EntityManager em = null;

        try (JPAContext ctx = new JPAContext()) {
            System.out.println("Station ID?");
            String stationID = reader.readLine();
            Integer stationIdInt = Integer.parseInt(stationID);

            emf = jakarta.persistence.Persistence.createEntityManagerFactory("projectSI");
            em = emf.createEntityManager();

            IDockRepository dockRepository = ctx.getDocks();
            var docks = dockRepository.findByStationId(stationIdInt);


            if (docks.isEmpty()) {
                System.out.println("Não existem docks registadas.");
            } else {
                for (var dock : docks) {
                    Query query = em.createNativeQuery("SELECT fx_dock_occupancy(?)");
                    query.setParameter(1, dock.getNumber());
                    Object result = query.getSingleResult();
                    double occupancy = (result != null) ? ((Number) result).doubleValue() : 0.0;
                    System.out.println("Station " +  stationID +" occupancy:" + occupancy * 100 + "%");
                    break;

                }
                System.out.printf("%-10s | %-10s\n", "Number", "State");
                System.out.println("-------------------------------------------");

                for (var dock : docks) {
                    System.out.printf("%-10d | %-10s\n",
                            dock.getNumber(),
                            dock.getState());
                }
            }

        } catch (Exception e) {
            System.out.println("Error listing docks: " + e.getMessage());
        } finally {
            if (em != null) em.close();
            if (emf != null) emf.close();
            System.out.println("\nPrima ENTER para continuar...");
            new Scanner(System.in).nextLine();
        }
    }


    private void startTrip() {

        System.out.println("Enter the client ID:");
        Integer clientId = new Scanner(System.in).nextInt();
        System.out.println("Enter the Scooter ID:");
        Integer scooterId = new Scanner(System.in).nextInt();

        EntityManagerFactory emf = null;
        EntityManager em = null;

        try {
            JPAContext ctx = new JPAContext();
            Integer res = ctx.startTrip(clientId,scooterId);

            if(res == 1){
                System.out.println("trip started successfully");
            }else{
                System.out.println("trip start failed");
            }




        }catch(Exception e){
            System.out.println("Error starting trip: " + e.getMessage());
        }
    }

    private void parkScooter()
    {
        System.out.println("Enter the scooter ID:");
        Integer scooterId = new Scanner(System.in).nextInt();

        System.out.println("Enter the dock ID:");
        Integer dockId = new Scanner(System.in).nextInt();

        try {
            JPAContext ctx = new JPAContext();
            Integer res = ctx.endTrip(scooterId,dockId);

            if(res == 1){
                System.out.println("scooter parked successfully");
            }else{
                System.out.println("scooter parking failed");
            }

        }catch(Exception e){
            System.out.println("Error starting trip: " + e.getMessage());
        }
        
    }

    private void about()
    {
        // TODO: Add your Group ID & member names
        System.out.println("DAL version:"+ Dal.version());
        System.out.println("Core version:"+ isel.sisinf.model.Core.version());
        
    }
}

public class App{
    public static void main(String[] args) throws Exception{
        UI.getInstance().Run();
    }
}