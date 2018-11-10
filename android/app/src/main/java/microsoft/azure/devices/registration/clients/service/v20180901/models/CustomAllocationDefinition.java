/**
 * Code generated by Microsoft (R) AutoRest Code Generator.
 * Changes may cause incorrect behavior and will be lost if the code is
 * regenerated.
 */

package microsoft.azure.devices.registration.clients.service.v20180901.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Custom allocation definition.
 */
public class CustomAllocationDefinition {
    /**
     * The webhook URL used for allocation requests.
     */
    @JsonProperty(value = "webhookUrl", required = true)
    private String webhookUrl;

    /**
     * The API version of the provisioning service types (such as
     * IndividualEnrollment) sent in the custom allocation request. Supported
     * versions include: "2018-09-01-preview".
     */
    @JsonProperty(value = "apiVersion", required = true)
    private String apiVersion;

    /**
     * Get the webhook URL used for allocation requests.
     *
     * @return the webhookUrl value
     */
    public String webhookUrl() {
        return this.webhookUrl;
    }

    /**
     * Set the webhook URL used for allocation requests.
     *
     * @param webhookUrl the webhookUrl value to set
     * @return the CustomAllocationDefinition object itself.
     */
    public CustomAllocationDefinition withWebhookUrl(String webhookUrl) {
        this.webhookUrl = webhookUrl;
        return this;
    }

    /**
     * Get the API version of the provisioning service types (such as IndividualEnrollment) sent in the custom allocation request. Supported versions include: "2018-09-01-preview".
     *
     * @return the apiVersion value
     */
    public String apiVersion() {
        return this.apiVersion;
    }

    /**
     * Set the API version of the provisioning service types (such as IndividualEnrollment) sent in the custom allocation request. Supported versions include: "2018-09-01-preview".
     *
     * @param apiVersion the apiVersion value to set
     * @return the CustomAllocationDefinition object itself.
     */
    public CustomAllocationDefinition withApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
        return this;
    }

}
