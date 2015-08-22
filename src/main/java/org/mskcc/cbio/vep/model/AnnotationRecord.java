/** Copyright (c) 2012 Memorial Sloan-Kettering Cancer Center.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY, WITHOUT EVEN THE IMPLIED WARRANTY OF
 * MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE.  The software and
 * documentation provided hereunder is on an "as is" basis, and
 * Memorial Sloan-Kettering Cancer Center 
 * has no obligations to provide maintenance, support,
 * updates, enhancements or modifications.  In no event shall
 * Memorial Sloan-Kettering Cancer Center
 * be liable to any party for direct, indirect, special,
 * incidental or consequential damages, including lost profits, arising
 * out of the use of this software and its documentation, even if
 * Memorial Sloan-Kettering Cancer Center 
 * has been advised of the possibility of such damage.
*/

package org.mskcc.cbio.vep.model;

/**
 * Encapsulates a single record from the Oncotator service.
 *
 * @author Selcuk Onur Sumer
 */
public class AnnotationRecord
{
	private String key;
	private String rawJson;
	private String genomeChange;
	private String dbSnpRs;
	private String dbSnpValStatus;
	private String cosmicOverlappingMutations;

	private Transcript bestCanonicalTranscript;
	private Transcript bestEffectTranscript;

    public AnnotationRecord(String key)
    {
        this.key = key;
	    this.bestCanonicalTranscript = new Transcript();
	    this.bestEffectTranscript = new Transcript();
    }

	// Getters and Setters

    public String getKey() {
        return key;
    }

	public String getRawJson()
	{
		return rawJson;
	}

	public void setRawJson(String rawJson)
	{
		this.rawJson = rawJson;
	}

    public String getGenomeChange() {
        return genomeChange;
    }

    public void setGenomeChange(String genomeChange) {
        this.genomeChange = genomeChange;
    }

    public String getCosmicOverlappingMutations() {
        return cosmicOverlappingMutations;
    }

    public void setCosmicOverlappingMutations(String cosmicOverlappingMutations) {
        this.cosmicOverlappingMutations = cosmicOverlappingMutations;
    }

    public String getDbSnpRs() {
        return dbSnpRs;
    }

    public void setDbSnpRs(String dbSnpRs) {
        this.dbSnpRs = dbSnpRs;
    }

	public String getDbSnpValStatus()
	{
		return dbSnpValStatus;
	}

	public void setDbSnpValStatus(String dbSnpValStatus)
	{
		this.dbSnpValStatus = dbSnpValStatus;
	}

	public Transcript getBestCanonicalTranscript()
	{
		return bestCanonicalTranscript;
	}

	public void setBestCanonicalTranscript(Transcript bestCanonicalTranscript)
	{
		this.bestCanonicalTranscript = bestCanonicalTranscript;
	}

	public Transcript getBestEffectTranscript()
	{
		return bestEffectTranscript;
	}

	public void setBestEffectTranscript(Transcript bestEffectTranscript)
	{
		this.bestEffectTranscript = bestEffectTranscript;
	}
}
