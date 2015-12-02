/*
 * GovPay - Porta di Accesso al Nodo dei Pagamenti SPC 
 * http://www.gov4j.it/govpay
 * 
 * Copyright (c) 2014-2015 Link.it srl (http://www.link.it).
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package it.govpay.utils;


import it.govpay.exception.GovPayException;
import it.govpay.exception.GovPayException.GovPayExceptionEnum;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConfigurationUtils {

	public boolean isSetup = false; 
	private static Logger logger = LogManager.getRootLogger();
	private Properties configProperties;

	public ConfigurationUtils(String filePropName) throws GovPayException{
		if(isSetup) return;
		String propertiesFilePath = getPropertyFilePath(filePropName);

		configProperties = new Properties();
		try {
			configProperties.load(new FileInputStream(propertiesFilePath));
		} catch (Exception ex) {
			try {
				configProperties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("/" + filePropName));
			} catch (Exception exx) {
				try {
					configProperties.load(ConfigurationUtils.class.getResourceAsStream("/" + filePropName));
				} catch (Exception exxx) {
					logger.error("Impossibile leggere il file di proprieta " + propertiesFilePath, ex);
					throw new GovPayException(GovPayExceptionEnum.ERRORE_CONFIGURAZIONE, "Impossibile leggere il file di proprieta " + propertiesFilePath, ex);
				}
			}
		}
		isSetup = true;
	}

	private static String getPropertyFilePath(String filePropName) {
		if(System.getProperty("it.govpay.config")!=null && System.getProperty("it.govpay.config").trim().length() !=0)
			return System.getProperty("it.govpay.config") + File.separatorChar + filePropName;
		else
			return filePropName;
	}

	public boolean getBooleanValue(String propertyName, boolean defaultValue) throws GovPayException {
		String value = configProperties.getProperty(propertyName);
		if(value == null || value.trim().length() == 0) { 
			logger.warn("Proprieta " + propertyName + " non valorizzata. Utilizzo il default [" + defaultValue + "]");
			return defaultValue;
		} else {
			return Boolean.parseBoolean(value.trim());
		}
	}


	public String getStringValue(String propertyName) throws GovPayException {
		String value = configProperties.getProperty(propertyName);
		if(value == null || value.trim().length() == 0) { 
			throw new GovPayException(GovPayExceptionEnum.ERRORE_CONFIGURAZIONE, "La proprieta' " + propertyName + " non e' stata valorizzata.");
		} else {
			return value.trim();
		}
	}

	public int getIntValue(String propertyName) throws GovPayException {
		String value = configProperties.getProperty(propertyName);
		if(value == null || value.trim().length() == 0) { 
			throw new GovPayException(GovPayExceptionEnum.ERRORE_CONFIGURAZIONE, "La proprieta' " + propertyName + " non e' stata valorizzata.");
		} else {
			return Integer.parseInt(value.trim());
		}
	}
	
	public Integer getNullableIntValue(String propertyName) throws GovPayException {
		String value = configProperties.getProperty(propertyName);
		if(value == null || value.trim().length() == 0) { 
			return null;
		} else {
			return Integer.parseInt(value.trim());
		}
	}
	
	public int getIntValue(String propertyName, int defaultValue) throws GovPayException {
		String value = configProperties.getProperty(propertyName);
		if(value == null || value.trim().length() == 0) { 
			logger.warn("Proprieta " + propertyName + " non valorizzata. Utilizzo il default [" + defaultValue + "]");
			return defaultValue;
		} else {
			return Integer.parseInt(value.trim());
		}
	}

	protected String getNullableStringValue(String propertyName) throws GovPayException {
		String value = configProperties.getProperty(propertyName);
		if(value == null || value.trim().length() == 0) { 
			return null;
		} else {
			return value.trim();
		}
	}

	public String getStringValue(String propertyName, String defaultValue) throws GovPayException {
		String value = configProperties.getProperty(propertyName);
		if(value == null || value.trim().length() == 0) { 
			logger.warn("Proprieta " + propertyName + " non valorizzata. Utilizzo il default [" + defaultValue + "]");
			return defaultValue;
		} else {
			return value.trim();
		}
	}
	
	public Properties getProperties() {
		return configProperties;
	}

	public boolean isSetup() {
		return isSetup;
	}
}
