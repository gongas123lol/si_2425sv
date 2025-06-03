/*
MIT License

Copyright (c) 2022-2024, Nuno Datia, ISEL

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
package isel.sisinf.repo;

import isel.sisinf.model.*;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.transaction.Transactional;
import org.eclipse.persistence.sessions.DatabaseLogin;
import org.eclipse.persistence.sessions.Session;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class JPAContext implements IContext {


    private EntityManagerFactory _emf;
    private EntityManager _em;

    private EntityTransaction _tx;
    private int _txcount;

    private IClientRepository _clientRepository;
    private IDockRepository _dockRepository;
    private IPersonRepository _personRepository;
    private IRiderRepository _riderRepository;

    /// HELPER METHODS
    protected List helperQueryImpl(String jpql, Object... params) {
        Query q = _em.createQuery(jpql);

        for (int i = 0; i < params.length; ++i)
            q.setParameter(i + 1, params[i]);

        return q.getResultList();
    }

    protected Object helperCreateImpl(Object entity) {
        beginTransaction();
        _em.persist(entity);
        commit();
        return entity;
    }

    protected Object helperUpdateImpl(Object entity) {
        beginTransaction();
        _em.merge(entity);
        commit();
        return entity;
    }

    protected Object helperDeleteteImpl(Object entity) {
        beginTransaction();
        _em.remove(entity);
        commit();
        return entity;
    }

    protected class DockRepository implements IDockRepository {

        private final EntityManager em;

        public DockRepository(EntityManager em) {
            this.em = em;
        }

        @Override
        public Optional<Dock> findById(Long id) {
            return Optional.ofNullable(em.find(Dock.class, id));
        }

        @Override
        public List<Dock> findAll() {
            return em.createQuery("SELECT d FROM Dock d", Dock.class).getResultList();
        }

        @Override
        public void save(Dock dock) {
            if (dock.getNumber() == null) {
                em.persist(dock);
            } else {
                em.merge(dock);
            }
        }

        @Override
        public void deleteById(Long id) {
            Dock dock = em.find(Dock.class, id);
            if (dock != null) {
                em.remove(dock);
            }
        }

        @Override
        public List<Dock> findByStationId(Integer stationId) {
            return em.createQuery("SELECT d FROM Dock d WHERE d.station.id = :stationId", Dock.class)
                    .setParameter("stationId", stationId)
                    .getResultList();
        }
    }

    protected class ClientRepository implements IClientRepository {

        private final EntityManager em;

        public ClientRepository(EntityManager em) {
            this.em = em;
        }

        @Override
        public Optional<Client> findById(Long id) {
            if (id == null) return Optional.empty();

            // Person.id is Integer → convert Long safely
            int personPk = Math.toIntExact(id);

            Person person = em.find(Person.class, personPk);
            return person == null
                    ? Optional.empty()
                    : Optional.ofNullable(em.find(Client.class, person));
        }

        @Override
        public List<Client> findAll() {
            return em.createQuery("select c from Client c", Client.class)
                    .getResultList();
        }

        @Override
        @Transactional
        public void save(Client client) {
            if (client == null) return;

            boolean exists = client.getPerson() != null &&
                    em.find(Client.class, client.getPerson()) != null;

            if (exists) {
                em.merge(client);   // UPDATE
            } else {
                em.persist(client); // INSERT
            }
        }

        @Override
        @Transactional
        public void deleteById(Long id) {
            if (id == null) return;

            int personPk = Math.toIntExact(id);

            Person person = em.find(Person.class, personPk);
            if (person == null) return;

            Client client = em.find(Client.class, person);
            if (client != null) {
                em.remove(client);
            }
        }
    }

    protected class PersonRepository implements IPersonRepository {

        private final EntityManager em;

        public PersonRepository(EntityManager em) {
            this.em = em;
        }

        @Override
        public Optional<Person> findById(Long id) {
            return Optional.ofNullable(em.find(Person.class, id));
        }

        @Override
        public List<Person> findAll() {
            return em.createQuery("SELECT p FROM Person p", Person.class).getResultList();
        }

        @Override
        public void save(Person person) {
            if (person.getId() == null) {
                em.persist(person);
            } else {
                em.merge(person);
            }
        }

        @Override
        public void deleteById(Long id) {
            Person person = em.find(Person.class, id);
            if (person != null) {
                em.remove(person);
            }
        }
    }

    protected class ScooterRepository implements IScooterRepository {
        private final EntityManager em;

        public ScooterRepository(EntityManager em) {
            this.em = em;
        }

        @Override
        public Optional<Scooter> findById(Long id) {
            return Optional.ofNullable(em.find(Scooter.class, id));
        }

        @Override
        public List<Scooter> findAll() {
            return em.createQuery("SELECT s FROM Scooter s", Scooter.class).getResultList();
        }

        @Override
        public void save(Scooter scooter) {
            if (scooter.getId() == null) {
                em.persist(scooter);
            } else {
                em.merge(scooter);
            }
        }

        @Override
        public void deleteById(Long id) {
            Scooter scooter = em.find(Scooter.class, id);
            if (scooter != null) {
                em.remove(scooter);
            }
        }
    }

    private IScooterRepository _scooterRepository;

    public IScooterRepository getScooters() {
        return _scooterRepository;
    }


    protected class RiderRepository implements IRiderRepository {

        private final EntityManager em;

        public RiderRepository(EntityManager em) {
            this.em = em;
        }

        @Override
        public Optional<Rider> findById(Long id) {
            return Optional.ofNullable(em.find(Rider.class, id));
        }

        @Override
        public List<Rider> findAll() {
           List<Rider> riders = em.createQuery("SELECT r FROM Rider r", Rider.class).getResultList();
            System.out.println(riders);
            return riders;
        }

        @Override
        public void save(Rider rider) {
            if (rider.getId() == null) {
                em.persist(rider);
            } else {
                em.merge(rider);
            }
        }

        @Override
        public void deleteById(Long id) {
            Rider rider = em.find(Rider.class, id);
            if (rider != null) {
                em.remove(rider);
            }
        }
    }

    @Override
    public void beginTransaction() {
        if (_tx == null) {
            _tx = _em.getTransaction();
            _tx.begin();
            _txcount = 0;
        }
        ++_txcount;
    }

    @Override
    public void beginTransaction(IsolationLevel isolationLevel) {
        beginTransaction();
        Session session = _em.unwrap(Session.class);
        DatabaseLogin databaseLogin = (DatabaseLogin) session.getDatasourceLogin();
        System.out.println(databaseLogin.getTransactionIsolation());

        int isolation = DatabaseLogin.TRANSACTION_READ_COMMITTED;
        if (isolationLevel == IsolationLevel.READ_UNCOMMITTED)
            isolation = DatabaseLogin.TRANSACTION_READ_UNCOMMITTED;
        else if (isolationLevel == IsolationLevel.REPEATABLE_READ)
            isolation = DatabaseLogin.TRANSACTION_REPEATABLE_READ;
        else if (isolationLevel == IsolationLevel.SERIALIZABLE)
            isolation = DatabaseLogin.TRANSACTION_SERIALIZABLE;

        databaseLogin.setTransactionIsolation(isolation);
    }

    @Override
    public void commit() {

        --_txcount;
        if (_txcount == 0 && _tx != null) {
            _em.flush(); //To assure all changes in memory go into the database
            _tx.commit();
            _tx = null;
        }
    }

    @Override
    public void flush() {
        _em.flush();
    }


    @Override
    public void clear() {
        _em.clear();

    }

    @Override
    public void persist(Object entity) {
        _em.persist(entity);

    }

    public JPAContext() {
        this("dal-lab");
    }

    public JPAContext(String persistentCtx) {
        super();

        this._emf = Persistence.createEntityManagerFactory(persistentCtx);
        this._em = _emf.createEntityManager();
        this._clientRepository = new ClientRepository(_em);
        this._dockRepository = new DockRepository(_em);
        this._personRepository = new PersonRepository(_em);
        this._riderRepository = new RiderRepository(_em);
        this._scooterRepository = new ScooterRepository(_em);

    }


    @Override
    public void close() throws Exception {

        if (_tx != null)
            _tx.rollback();
        _em.close();
        _emf.close();
    }

    //@Override
    public IDockRepository getDocks() {
        return _dockRepository;
    }

    @Override
    public IClientRepository getClients() {
        return _clientRepository;
    }

    @Override
    public IPersonRepository getPersons() {
        return _personRepository;
    }

    @Override
    public IRiderRepository getRiders() {
        return _riderRepository;
    }

    /// functions and stored procedure
    public java.math.BigDecimal rand_fx(int seed) {

        StoredProcedureQuery namedrand_fx =
                _em.createNamedStoredProcedureQuery("namedrand_fx");
        namedrand_fx.setParameter(1, seed);
        namedrand_fx.execute();

        return (java.math.BigDecimal) namedrand_fx.getOutputParameterValue(2);
    }

    public Integer startTrip(Integer clientId, Integer dockId){
        StoredProcedureQuery startTrip = _em.createNamedStoredProcedureQuery("startTrip");
        startTrip.setParameter(2, clientId);
        startTrip.setParameter(1, dockId);
        startTrip.execute();
        //System.out.println(startTrip.getResultList());
        return 1;
    }

    public Integer endTrip(Integer scooterId, Integer dockId) {
        try {
            beginTransaction();

            // get dock
            Dock dock = _em.find(Dock.class, Long.valueOf(dockId));
            if (dock == null || !"free".equals(dock.getState())) {
                throw new IllegalStateException("Dock inválida ou ocupada.");
            }
            Scooter scooter = _scooterRepository.findById(Long.valueOf(scooterId)).orElse(null);
            TypedQuery<Travel> q = _em.createQuery(
                    "SELECT t FROM Travel t WHERE t.scooter = :scooterID AND t.endTime IS NULL",
                    Travel.class
            );
            q.setParameter("scooterID", scooter);


            List<Travel> results = q.getResultList();
            if (results.isEmpty()) {
                throw new IllegalStateException("O cliente não tem nenhuma viagem ativa.");
            }

            Travel travel = results.get(0);

            // update travel
            travel.setEndTime(LocalDateTime.now());
            travel.setEndDock(dock);

            //update dock
            dock.setState("occupy");
            dock.setScooter(scooter);

            _em.merge(travel);
            _em.merge(dock);

            commit();
            return 1;

        } catch (OptimisticLockException e) {
            if (_tx != null && _tx.isActive()) _tx.rollback();
            throw new RuntimeException("OptimisticLockException: "+ e.getMessage());
        } catch (Exception e) {
            if (_tx != null && _tx.isActive()) _tx.rollback();
            e.printStackTrace();
            return 0;
        }
    }



}
