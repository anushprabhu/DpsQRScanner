/**
 * Code generated by Microsoft (R) AutoRest Code Generator.
 * Changes may cause incorrect behavior and will be lost if the code is
 * regenerated.
 */

package microsoft.azure.devices.registration.clients.service.v20180901.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Attestation via SymmetricKey.
 */
public class SymmetricKeyAttestation {
    /**
     * Primary symmetric key.
     */
    @JsonProperty(value = "primaryKey")
    private String primaryKey;

    /**
     * Secondary symmetric key.
     */
    @JsonProperty(value = "secondaryKey")
    private String secondaryKey;

    /**
     * Get primary symmetric key.
     *
     * @return the primaryKey value
     */
    public String primaryKey() {
        return this.primaryKey;
    }

    /**
     * Set primary symmetric key.
     *
     * @param primaryKey the primaryKey value to set
     * @return the SymmetricKeyAttestation object itself.
     */
    public SymmetricKeyAttestation withPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
        return this;
    }

    /**
     * Get secondary symmetric key.
     *
     * @return the secondaryKey value
     */
    public String secondaryKey() {
        return this.secondaryKey;
    }

    /**
     * Set secondary symmetric key.
     *
     * @param secondaryKey the secondaryKey value to set
     * @return the SymmetricKeyAttestation object itself.
     */
    public SymmetricKeyAttestation withSecondaryKey(String secondaryKey) {
        this.secondaryKey = secondaryKey;
        return this;
    }

}
