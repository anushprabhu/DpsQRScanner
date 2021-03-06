package com.microsoft.azure.devices.dps.dpsqrscanner;

/**

 * Class used to abstract the different

 * authentication methods used to use Provisioning SDK.

 *

 */

public abstract class AuthenticationMethod

{

    /**

     * Authentication policy name

     */

    protected String policyName;



    /**

     * Shared access policy key (if applies)

     */

    protected String key;



    /**

     * Shared access policy token (if applies)

     */

    protected String token;



    /**

     * Abstract helper function to populate {@code IotHubConnectionString} class with

     * proper authentication properties

     *

     * @param provisioningConnectionString  The {@link ProvisioningConnectionString} that is

     *                                       to be populated with the authentication

     *                                       properties.

     * @return An instance of {@link ProvisioningConnectionString} populated with the

     * the authentication properties.

     */

    protected abstract ProvisioningConnectionString populateWithAuthenticationProperties(ProvisioningConnectionString provisioningConnectionString);

}
