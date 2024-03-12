//package com.ftp.osmserverproj.Model;

/*import jakarta.persistence.*;
//import lombok.Data;

@Entity
//@Data
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    //@Column(name = "ID_PRODUIT")
    //private Long id;
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
    private String coutDeRevient;// private Double coutDeRevient; TBIDALHA STRING lin yjeweb ahmed

    @Column(name = "MONTANT",nullable = true)
    private String montant;

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
    private String faxIsMandatory;//Integer

    @Column(name = "APN_IS_MANDATORY",nullable = true)
    private String apnIsMandatory;//Integer

    @Column(name = "CRM_ARPU_OUT",nullable = true)
    private String  crmArpuOut;//Double

    @Column(name = "CRM_ARPU_IN",nullable = true)
    private String crmArpuIn;//Double

    @Column(name = "CRM_COUT_DIRECT",nullable = true)
    private String crmCoutDirect;//Double

    @Column(name = "CRM_COUT_EXPLOITATION",nullable = true)
    private String crmCoutExploitation;//Double

    @Column(name = "CRM_COUT_ACQUISITION",nullable = true)
    private String crmCoutAcquisition;//Double

    @Column(name = "CRM_FORFAIT_HT_HORS_RECHARGE",nullable = true)
    private String crmForfaitHtHorsRecharge;//Double

    @Column(name = "CRM_RECHARGE",nullable = true)
    private String crmRecharge;//Double

    @Column(name = "CRM_BUDGET_SUBVENTION_12",nullable = true)
    private String crmBudgetSubvention12;//Double

    @Column(name = "CRM_BUDGET_SUBVENTION_24",nullable = true)
    private String crmBudgetSubvention24;//Double

    @Column(name = "CRM_BUDGET_SUBVENTION_36",nullable = true)
    private String crmBudgetSubvention36;//Double
    //public Long getId() { return id; }

    //public void setId(Long id) { this.id = id;}

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getNomProduit() {
        return nomProduit;
    }

    public void setNomProduit(String nomProduit) {
        this.nomProduit = nomProduit;
    }

    public String getTypeProduit() {
        return typeProduit;
    }

    public void setTypeProduit(String typeProduit) {
        this.typeProduit = typeProduit;
    }

    public String getFamilleOption() {
        return familleOption;
    }

    public void setFamilleOption(String familleOption) {
        this.familleOption = familleOption;
    }

    public String getTarifParDefaut() {
        return tarifParDefaut;
    }

    public void setTarifParDefaut(String tarifParDefaut) {
        this.tarifParDefaut = tarifParDefaut;
    }

    public Double getTarifHT() {
        return tarifHT;
    }

    public void setTarifHT(Double tarifHT) {
        this.tarifHT = tarifHT;
    }

    public Double getTarifTTC() {
        return tarifTTC;
    }

    public void setTarifTTC(Double tarifTTC) {
        this.tarifTTC = tarifTTC;
    }

    public String getCoutDeRevient() {
        return coutDeRevient;
    }

    public void setCoutDeRevient(String coutDeRevient) {
        this.coutDeRevient = coutDeRevient;
    }

    public String getMontant() {
        return montant;
    }

    public void setMontant(String montant) {
        this.montant = montant;
    }

    public String getOfferShdes() {
        return offerShdes;
    }

    public void setOfferShdes(String offerShdes) {
        this.offerShdes = offerShdes;
    }

    public String getOfferValue() {
        return offerValue;
    }

    public void setOfferValue(String offerValue) {
        this.offerValue = offerValue;
    }

    public String getServiceShdes() {
        return serviceShdes;
    }

    public void setServiceShdes(String serviceShdes) {
        this.serviceShdes = serviceShdes;
    }

    public String getPackageShdes() {
        return packageShdes;
    }

    public void setPackageShdes(String packageShdes) {
        this.packageShdes = packageShdes;
    }

    public String getParameterShdes() {
        return parameterShdes;
    }

    public void setParameterShdes(String parameterShdes) {
        this.parameterShdes = parameterShdes;
    }

    public String getParameterValue() {
        return parameterValue;
    }

    public void setParameterValue(String parameterValue) {
        this.parameterValue = parameterValue;
    }

    public String getParameterValueDes() {
        return parameterValueDes;
    }

    public void setParameterValueDes(String parameterValueDes) {
        this.parameterValueDes = parameterValueDes;
    }

    public Integer getParameterNo() {
        return parameterNo;
    }

    public void setParameterNo(Integer parameterNo) {
        this.parameterNo = parameterNo;
    }

    public String getFaxIsMandatory() {
        return faxIsMandatory;
    }

    public void setFaxIsMandatory(String faxIsMandatory) {
        this.faxIsMandatory = faxIsMandatory;
    }

    public String getApnIsMandatory() {
        return apnIsMandatory;
    }

    public void setApnIsMandatory(String apnIsMandatory) {
        this.apnIsMandatory = apnIsMandatory;
    }

    public String getCrmArpuOut() {
        return crmArpuOut;
    }

    public void setCrmArpuOut(String crmArpuOut) {
        this.crmArpuOut = crmArpuOut;
    }

    public String getCrmArpuIn() {
        return crmArpuIn;
    }

    public void setCrmArpuIn(String crmArpuIn) {
        this.crmArpuIn = crmArpuIn;
    }

    public String getCrmCoutDirect() {
        return crmCoutDirect;
    }

    public void setCrmCoutDirect(String crmCoutDirect) {
        this.crmCoutDirect = crmCoutDirect;
    }

    public String getCrmCoutExploitation() {
        return crmCoutExploitation;
    }

    public void setCrmCoutExploitation(String crmCoutExploitation) {
        this.crmCoutExploitation = crmCoutExploitation;
    }

    public String getCrmCoutAcquisition() {
        return crmCoutAcquisition;
    }

    public void setCrmCoutAcquisition(String crmCoutAcquisition) {
        this.crmCoutAcquisition = crmCoutAcquisition;
    }

    public String getCrmForfaitHtHorsRecharge() {
        return crmForfaitHtHorsRecharge;
    }

    public void setCrmForfaitHtHorsRecharge(String crmForfaitHtHorsRecharge) {
        this.crmForfaitHtHorsRecharge = crmForfaitHtHorsRecharge;
    }

    public String getCrmRecharge() {
        return crmRecharge;
    }

    public void setCrmRecharge(String crmRecharge) {
        this.crmRecharge = crmRecharge;
    }

    public String getCrmBudgetSubvention12() {
        return crmBudgetSubvention12;
    }

    public void setCrmBudgetSubvention12(String crmBudgetSubvention12) {
        this.crmBudgetSubvention12 = crmBudgetSubvention12;
    }

    public String getCrmBudgetSubvention24() {
        return crmBudgetSubvention24;
    }

    public void setCrmBudgetSubvention24(String crmBudgetSubvention24) {
        this.crmBudgetSubvention24 = crmBudgetSubvention24;
    }

    public String getCrmBudgetSubvention36() {
        return crmBudgetSubvention36;
    }

    public void setCrmBudgetSubvention36(String crmBudgetSubvention36) {
        this.crmBudgetSubvention36 = crmBudgetSubvention36;
    }
}*/
package com.ftp.osmserverproj.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {
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

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getNomProduit() {
        return nomProduit;
    }

    public void setNomProduit(String nomProduit) {
        this.nomProduit = nomProduit;
    }

    public String getTypeProduit() {
        return typeProduit;
    }

    public void setTypeProduit(String typeProduit) {
        this.typeProduit = typeProduit;
    }

    public String getFamilleOption() {
        return familleOption;
    }

    public void setFamilleOption(String familleOption) {
        this.familleOption = familleOption;
    }

    public String getTarifParDefaut() {
        return tarifParDefaut;
    }

    public void setTarifParDefaut(String tarifParDefaut) {
        this.tarifParDefaut = tarifParDefaut;
    }

    public Double getTarifHT() {
        return tarifHT;
    }

    public void setTarifHT(Double tarifHT) {
        this.tarifHT = tarifHT;
    }

    public Double getTarifTTC() {
        return tarifTTC;
    }

    public void setTarifTTC(Double tarifTTC) {
        this.tarifTTC = tarifTTC;
    }

    public Double getCoutDeRevient() {
        return coutDeRevient;
    }

    public void setCoutDeRevient(Double coutDeRevient) {
        this.coutDeRevient = coutDeRevient;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public String getOfferShdes() {
        return offerShdes;
    }

    public void setOfferShdes(String offerShdes) {
        this.offerShdes = offerShdes;
    }

    public String getOfferValue() {
        return offerValue;
    }

    public void setOfferValue(String offerValue) {
        this.offerValue = offerValue;
    }

    public String getServiceShdes() {
        return serviceShdes;
    }

    public void setServiceShdes(String serviceShdes) {
        this.serviceShdes = serviceShdes;
    }

    public String getPackageShdes() {
        return packageShdes;
    }

    public void setPackageShdes(String packageShdes) {
        this.packageShdes = packageShdes;
    }

    public String getParameterShdes() {
        return parameterShdes;
    }

    public void setParameterShdes(String parameterShdes) {
        this.parameterShdes = parameterShdes;
    }

    public String getParameterValue() {
        return parameterValue;
    }

    public void setParameterValue(String parameterValue) {
        this.parameterValue = parameterValue;
    }

    public String getParameterValueDes() {
        return parameterValueDes;
    }

    public void setParameterValueDes(String parameterValueDes) {
        this.parameterValueDes = parameterValueDes;
    }

    public Integer getParameterNo() {
        return parameterNo;
    }

    public void setParameterNo(Integer parameterNo) {
        this.parameterNo = parameterNo;
    }

    public String getFaxIsMandatory() {
        return faxIsMandatory;
    }

    public void setFaxIsMandatory(String faxIsMandatory) {
        this.faxIsMandatory = faxIsMandatory;
    }

    public String getApnIsMandatory() {
        return apnIsMandatory;
    }

    public void setApnIsMandatory(String apnIsMandatory) {
        this.apnIsMandatory = apnIsMandatory;
    }

    public String getCrmArpuOut() {
        return crmArpuOut;
    }

    public void setCrmArpuOut(String crmArpuOut) {
        this.crmArpuOut = crmArpuOut;
    }

    public String getCrmArpuIn() {
        return crmArpuIn;
    }

    public void setCrmArpuIn(String crmArpuIn) {
        this.crmArpuIn = crmArpuIn;
    }

    public String getCrmCoutDirect() {
        return crmCoutDirect;
    }

    public void setCrmCoutDirect(String crmCoutDirect) {
        this.crmCoutDirect = crmCoutDirect;
    }

    public String getCrmCoutExploitation() {
        return crmCoutExploitation;
    }

    public void setCrmCoutExploitation(String crmCoutExploitation) {
        this.crmCoutExploitation = crmCoutExploitation;
    }

    public String getCrmCoutAcquisition() {
        return crmCoutAcquisition;
    }

    public void setCrmCoutAcquisition(String crmCoutAcquisition) {
        this.crmCoutAcquisition = crmCoutAcquisition;
    }

    public String getCrmForfaitHtHorsRecharge() {
        return crmForfaitHtHorsRecharge;
    }

    public void setCrmForfaitHtHorsRecharge(String crmForfaitHtHorsRecharge) {
        this.crmForfaitHtHorsRecharge = crmForfaitHtHorsRecharge;
    }

    public String getCrmRecharge() {
        return crmRecharge;
    }

    public void setCrmRecharge(String crmRecharge) {
        this.crmRecharge = crmRecharge;
    }

    public String getCrmBudgetSubvention12() {
        return crmBudgetSubvention12;
    }

    public void setCrmBudgetSubvention12(String crmBudgetSubvention12) {
        this.crmBudgetSubvention12 = crmBudgetSubvention12;
    }

    public String getCrmBudgetSubvention24() {
        return crmBudgetSubvention24;
    }

    public void setCrmBudgetSubvention24(String crmBudgetSubvention24) {
        this.crmBudgetSubvention24 = crmBudgetSubvention24;
    }

    public String getCrmBudgetSubvention36() {
        return crmBudgetSubvention36;
    }

    public void setCrmBudgetSubvention36(String crmBudgetSubvention36) {
        this.crmBudgetSubvention36 = crmBudgetSubvention36;
    }
}


