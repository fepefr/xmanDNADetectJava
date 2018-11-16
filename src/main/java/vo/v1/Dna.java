package vo.v1;
public class Dna{

    private String _id;
    private String _rev;
    private String dna = null;

    public Dna() {
        this.setDna("");
    }

    /**
     * Gets the ID.
     * 
     * @return The ID.
     */
    public String get_id() {
        return _id;
    }

    /**
     * Sets the ID
     * 
     * @param _id
     *            The ID to set.
     */
    public void set_id(String _id) {
        this._id = _id;
    }

    /**
     * Gets the revision of the document.
     * 
     * @return The revision of the document.
     */
    public String get_rev() {
        return _rev;
    }

    /**
     * Sets the revision.
     * 
     * @param _rev
     *            The revision to set.
     */
    public void set_rev(String _rev) {
        this._rev = _rev;
    }

	public String getDna() {
		return dna;
	}

	public void setDna(String dna) {
		this.dna = dna;
	}
   	
   	
}