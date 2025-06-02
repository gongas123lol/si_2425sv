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

import java.util.List;
import java.util.Scanner;
import java.util.HashMap;
import java.io.InputStreamReader;

import isel.sisinf.Dal;
import isel.sisinf.repo.IClientRepository;
import isel.sisinf.repo.IPersonRepository;
import isel.sisinf.model.Client;
import isel.sisinf.ClientServices;
import isel.sisinf.repo.JPAContext;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.io.BufferedReader;
import java.io.IOException;

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
        EntityManagerFactory emf = null;
        EntityManager em = null;

        try {
            System.out.println("Nome?");
            String name = reader.readLine();
            System.out.println("Email?");
            String email = reader.readLine();
            System.out.println("NIF?");
            String taxnrStr = reader.readLine();
            Integer taxnr = Integer.valueOf(taxnrStr);

            emf = jakarta.persistence.Persistence.createEntityManagerFactory("citesPU");
            em  = emf.createEntityManager();

            try (JPAContext ctx = new JPAContext()) {
                IPersonRepository personRepository = ctx.getPersons();
                IClientRepository clientRepository = ctx.getClients();
                ClientServices clientService = new ClientServices(personRepository, clientRepository, em);

                em.getTransaction().begin();
                clientService.createPersonAndClient(name, email, taxnr);
                em.getTransaction().commit();

                System.out.println("Cliente criado com sucesso!");
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler dados do usuário: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro ao criar cliente: " + e.getMessage());
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            if (em != null) em.close();
            if (emf != null) emf.close();
        }
    }



    private void listCostumer() {
    EntityManagerFactory emf = null;
    EntityManager em = null;
    
    try {

        emf = jakarta.persistence.Persistence.createEntityManagerFactory("citesPU");
        em = emf.createEntityManager();

        try (JPAContext ctx = new JPAContext()) {
            IPersonRepository personRepository = ctx.getPersons();
            IClientRepository clientRepository = ctx.getClients();

        ClientServices clientServices = new ClientServices(personRepository,clientRepository,em);
        List<Client> users = clientServices.listClient();

        if (users.isEmpty()) {
            System.out.println("No customers found.");
        } else {
            System.out.println("Customer List:");
            System.out.println("----------------------------------------");
            for (Client user : users) {
                System.out.printf("ID: %-5d | NIF: %-12s | Name: %-20s | Join Date: %s%n",
                    user.getPerson().getId(),
                    user.getPerson().getTaxNumber(),
                    user.getPerson().getName(),
                    user.getDtRegister().toString());
            }
            System.out.println("----------------------------------------");
            System.out.println("Total customers: " + users.size());
        }
        }
    } catch (Exception e) {
        System.out.println("Error listing customers: " + e.getMessage());
    } finally {
        if (em != null) em.close();
        if (emf != null) emf.close();
    }
}

    private void listDocks()
    {
        // TODO
        System.out.println("listDocks()");

    }

    private void startTrip() {
        // TODO
        System.out.println("startTrip()");
    }

    private void parkScooter()
    {
        // TODO
        System.out.println("parkScooter()");
        
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