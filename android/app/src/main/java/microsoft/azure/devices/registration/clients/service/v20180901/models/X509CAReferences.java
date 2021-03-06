/**
 * Code generated by Microsoft (R) AutoRest Code Generator.
 * Changes may cause incorrect behavior and will be lost if the code is
 * regenerated.
 */

package microsoft.azure.devices.registration.clients.service.v20180901.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Primary and secondary CA references.
 */
public class X509CAReferences {
    /**
     * The primary property.
     */
    @JsonProperty(value = "primary")
    private String primary;

    /**
     * The secondary property.
     */
    @JsonProperty(value = "secondary")
    private String secondary;

    /**
     * Get the primary value.
     *
     * @return the primary value
     */
    public String primary() {
        return this.primary;
    }

    /**
     * Set the primary value.
     *
     * @param primary the primary value to set
     * @return the X509CAReferences object itself.
     */
    public X509CAReferences withPrimary(String primary) {
        this.primary = primary;
        return this;
    }

    /**
     * Get the secondary value.
     *
     * @return the secondary value
     */
    public String secondary() {
        return this.secondary;
    }

    /**
     * Set the secondary value.
     *
     * @param secondary the secondary value to set
     * @return the X509CAReferences object itself.
     */
    public X509CAReferences withSecondary(String secondary) {
        this.secondary = secondary;
        return this;
    }

}
