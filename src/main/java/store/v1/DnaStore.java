package store.v1;

import java.util.Collection;

import vo.v1.Dna;

/**
 * Defines the API for a Dna store.
 *
 */
public interface DnaStore {

    /**
     * Get the target db object.
     * 
     * @return Database.
     * @throws Exception 
     */
    public Object getDB();

  
    /**
     * Gets all Dnas from the store.
     * 
     * @return All Dnas.
     * @throws Exception 
     */
    public Collection<Dna> getAll();

    /**
     * Gets an indidnadual Dna from the store.
     * @param id The ID of the Dna to get.
     * @return The Dna.
     */
    public Dna get(String id);

    /**
     * Persists a Dna to the store.
     * @param dna The dna to persist.
     * @return The persisted Dna.  The Dna will not have a unique ID..
     */
    public Dna persist(Dna dna);

    /**
     * Updates a Dna in the store.
     * @param id The ID of the Dna to update.
     * @param td The Dna with updated information.
     * @return The updated Dna.
     */
    public Dna update(String id, Dna dna);

    /**
     * Deletes a Dna from the store.
     * @param id The ID of the Dna to delete.
     */
    public void delete(String id);
  
    /**
     * Counts the number of Dnas
     * @return The total number of Dnas.
     * @throws Exception 
     */
    public int count() throws Exception;
}