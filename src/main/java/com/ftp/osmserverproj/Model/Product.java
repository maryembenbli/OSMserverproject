package com.ftp.osmserverproj.Model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
//import lombok.Data;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "products")
@Data
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ID_PRODUIT")
    private String productId;

    @Column(name = "NATURE",nullable = true)
    private String nature;

    @Column(name = "NOM_PRODUIT",nullable = true)
    private String nomProduit;

    @Column(name = "TYPE_PRODUIT",nullable = true)
    private String typeProduit;

    @Column(name = "FAMILLE_OPTION",nullable = true)
    private String familleOption;

    @Column(name = "TARIF_PAR_DEFAUT",nullable = true)
    private String tarifParDefaut;

    @Column(name = "TARIF_HT",nullable = true)
    private Double tarifHT;

    @Column(name = "TARIF_TTC",nullable = true)
    private Double tarifTTC;

    @Column(name = "COUT_DE_REVIENT",nullable = true)
    private Double coutDeRevient;

    @Column(name = "MONTANT",nullable = true)
    private Double montant;

    @Column(name = "OFFER_SHDES",nullable = true)
    private String offerShdes;

    @Column(name = "OFFER_VALUE",nullable = true)
    private String offerValue;

    @Column(name = "SERVICE_SHDES",nullable = true)
    private String serviceShdes;

    @Column(name = "PACKAGE_SHDES",nullable = true)
    private String packageShdes;

    @Column(name = "PARAMETER_SHDES",nullable = true)
    private String parameterShdes;

    @Column(name = "PARAMETER_VALUE",nullable = true)
    private String parameterValue;

    @Column(name = "PARAMETER_VALUE_DES",nullable = true)
    private String parameterValueDes;

    @Column(name = "PARAMETER_NO",nullable = true)
    private Integer parameterNo;

    @Column(name = "FAX_IS_MANDATORY",nullable = true)
    private String faxIsMandatory;

    @Column(name = "APN_IS_MANDATORY",nullable = true)
    private String apnIsMandatory;

    @Column(name = "CRM_ARPU_OUT",nullable = true)
    private String  crmArpuOut;

    @Column(name = "CRM_ARPU_IN",nullable = true)
    private String crmArpuIn;

    @Column(name = "CRM_COUT_DIRECT",nullable = true)
    private String crmCoutDirect;

    @Column(name = "CRM_COUT_EXPLOITATION",nullable = true)
    private String crmCoutExploitation;

    @Column(name = "CRM_COUT_ACQUISITION",nullable = true)
    private String crmCoutAcquisition;

    @Column(name = "CRM_FORFAIT_HT_HORS_RECHARGE",nullable = true)
    private String crmForfaitHtHorsRecharge;

    @Column(name = "CRM_RECHARGE",nullable = true)
    private String crmRecharge;

    @Column(name = "CRM_BUDGET_SUBVENTION_12",nullable = true)
    private String crmBudgetSubvention12;

    @Column(name = "CRM_BUDGET_SUBVENTION_24",nullable = true)
    private String crmBudgetSubvention24;

    @Column(name = "CRM_BUDGET_SUBVENTION_36",nullable = true)
    private String crmBudgetSubvention36;
    @Column(name = "catalog_Id")
    private Long catalogId;

    @ManyToOne
    @JoinColumn(name = "catalog_Id", referencedColumnName = "id", insertable = false, updatable = false)
    private Catalog catalog;

}


