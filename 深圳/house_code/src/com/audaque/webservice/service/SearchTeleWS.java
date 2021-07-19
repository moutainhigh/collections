/**
 * SearchTeleWS.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.audaque.webservice.service;

public interface SearchTeleWS extends java.rmi.Remote {
    public java.lang.String searchTele_Houses(java.lang.String key, java.lang.String name, java.lang.String code, java.lang.Integer page, java.lang.Integer rows) throws java.rmi.RemoteException;
    public java.lang.String searchTele_Building(java.lang.String key, java.lang.String name, java.lang.String code, java.lang.Integer page, java.lang.Integer rows) throws java.rmi.RemoteException;
    public java.lang.String building_Houses(java.lang.String key, java.lang.String building_id, java.lang.Integer page, java.lang.Integer rows) throws java.rmi.RemoteException;
    public java.lang.String houses_MoreAddress(java.lang.String key, java.lang.String name, java.lang.String code, java.lang.Integer page, java.lang.Integer rows) throws java.rmi.RemoteException;
}
