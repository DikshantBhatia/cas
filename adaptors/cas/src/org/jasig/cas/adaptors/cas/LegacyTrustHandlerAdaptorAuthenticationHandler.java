/* Copyright 2004 The JA-SIG Collaborative.  All rights reserved.
 * See license distributed with this file and
 * available online at http://www.uportal.org/license.html
 */
package org.jasig.cas.adaptors.cas;

import org.jasig.cas.authentication.AuthenticationException;
import org.jasig.cas.authentication.handler.AuthenticationHandler;
import org.jasig.cas.authentication.principal.Credentials;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.StringUtils;

import edu.yale.its.tp.cas.auth.TrustHandler;

/**
 * Adaptor class to adapt the legacy CAS TrustHandler to the new AuthenticationHandler
 * 
 * @author Scott Battaglia
 * @version $Id$
 *
 */
public class LegacyTrustHandlerAdaptorAuthenticationHandler implements AuthenticationHandler, InitializingBean {

    private TrustHandler trustHandler;

    /**
     * @see org.jasig.cas.authentication.handler.AuthenticationHandler#authenticate(org.jasig.cas.authentication.principal.Credentials)
     */
    public boolean authenticate(final Credentials credentials) throws AuthenticationException {
        final LegacyCasTrustedCredentials casCredentials = (LegacyCasTrustedCredentials)credentials;

        return StringUtils.hasText(trustHandler.getUsername(casCredentials.getServletRequest()));
    }

    /**
     * @see org.jasig.cas.authentication.handler.AuthenticationHandler#supports(org.jasig.cas.authentication.principal.Credentials)
     */
    public boolean supports(final Credentials credentials) {
        if (credentials == null)
            return false;

        return LegacyCasTrustedCredentials.class.equals(credentials.getClass());
    }

    /**
     * @param passwordHandler The passwordHandler to set.
     */
    public void setTrustHandler(final TrustHandler trustHandler) {
        this.trustHandler = trustHandler;
    }

    /**
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    public void afterPropertiesSet() throws Exception {
        if (this.trustHandler == null) {
            throw new IllegalStateException("trustHandler must be set on " + this.getClass().getName());
        }
    }
}
