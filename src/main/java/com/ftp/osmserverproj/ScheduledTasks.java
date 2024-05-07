package com.ftp.osmserverproj;

import com.ftp.osmserverproj.Config.GateeFile;
import com.ftp.osmserverproj.Model.Catalog;
import com.ftp.osmserverproj.Model.EmailDetails;
import com.ftp.osmserverproj.Model.Group;
import com.ftp.osmserverproj.Model.Product;
import com.ftp.osmserverproj.Service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class ScheduledTasks {
    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);
    private static final String SUCCESS_FOLDER = "C:/apache-ftpserver-1.1.3-bin/res/home/Success/";
    private static final String FAILED_FOLDER = "C:/apache-ftpserver-1.1.3-bin/res/home/Failed/";

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    @Autowired
    private final ProductService productService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private CatalogService catalogService;
    @Autowired
    private GroupService groupService;


    public ScheduledTasks(ProductService productService, GateeFile gateFile) {
        this.productService = productService;
        this.gateFile = gateFile;
    }

    @Scheduled(cron = "0 */1 * * * ?") //0 8-18/4 * * *  (from 8am to 6pm every 4 hour)


   /* public void uploadFilesAndSendEmails() {
        try {
            logger.info("Cron Task :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
            List<String> fileNames = getFileNamesFromFolder("C:/apache-ftpserver-1.1.3-bin/res/home");
            String attachmentPath = "C:/apache-ftpserver-1.1.3-bin/res/home/";

            if (fileNames.isEmpty()) {
                logger.info("No files found in the specified path.");
                return;
            }

            for (String fileName : fileNames) {
                String filePath =  fileName;//attachmentPath +
                String uploadResult = uploadFile(filePath);

                EmailDetails emailDetails = new EmailDetails();
                emailDetails.setRecipient("meryembenbli4@gmail.com");
                emailDetails.setSubject("Uploaded Files: " + fileName); // Subject includes file name

                if (uploadResult.contains("Inserted values")) {
                    emailDetails.setMsgBody("Catalog uploaded successfully.");
                    sendEmailNotification("meryembenbli4@gmail.com", "Catalog uploaded successfully.", filePath);
                    historyService.saveHistory("Upload successful", LocalDateTime.now(), emailDetails);
                } else if (uploadResult.contains("Invalid number of values in line: ")) {
                    emailDetails.setMsgBody("Failed to upload catalog.");
                    emailDetails.setAttachment(filePath);
                    sendEmailNotification("meryembenbli4@gmail.com", "Failed to upload catalog.", filePath);
                    historyService.saveHistory("Upload failed", LocalDateTime.now(), emailDetails);
                }
            }
        } catch (IOException e) {
            logger.error("Error occurred while processing files: {}", e.getMessage());
            // Handle the exception as needed, such as logging or throwing it further
        }
    }*/
    public void uploadFilesAndSendEmails() {
        try {
            logger.info("Cron Task :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
            List<String> fileNames = getFileNamesFromFolder("C:/apache-ftpserver-1.1.3-bin/res/home");

            if (fileNames.isEmpty()) {
                logger.info("No files found in the specified path.");
                return;
            }

            for (String fileName : fileNames) {
                // Check if the file has already been uploaded based on file name or subject
                if (fileAlreadyUploaded(fileName)) {
                    logger.info("File {} already uploaded. Skipping...", fileName);
                    sendFileAlreadyExistsNotification(fileName);
                    moveFile(fileName, FAILED_FOLDER);
                    continue;
                }

                String uploadResult = uploadFile(fileName);
                String subject = "Uploaded Files: " + fileName;

                EmailDetails emailDetails = new EmailDetails();
                emailDetails.setRecipient("meryembenbli4@gmail.com");
                emailDetails.setSubject(subject);

/*if (uploadResult.contains("Inserted values")) {
                    emailDetails.setMsgBody("Catalog uploaded successfully.");
                    emailDetails.setAttachment(fileName);
                    sendEmailNotification("meryembenbli4@gmail.com", "Catalog uploaded successfully.", fileName);
                    historyService.saveHistory("Upload successful", LocalDateTime.now(), emailDetails);
                } else if (uploadResult.contains("Invalid number of values in line: ")) {
                    emailDetails.setMsgBody("Failed to upload catalog.");
                    emailDetails.setAttachment(fileName);
                    sendEmailNotification("meryembenbli4@gmail.com", "Failed to upload catalog.", fileName);
                    historyService.saveHistory("Upload failed", LocalDateTime.now(), emailDetails);
                }*/
                if (uploadResult.contains("Inserted values")) {
                    String successMsg = "Catalog uploaded successfully.";
                    emailDetails.setMsgBody(successMsg);
                    emailDetails.setAttachment(fileName);
                    sendEmailNotification("meryembenbli4@gmail.com", "Catalog uploaded successfully.", successMsg, fileName);
                    historyService.saveHistory("Upload successful", LocalDateTime.now(), emailDetails);
                    moveFile(fileName, SUCCESS_FOLDER);
                } else if (uploadResult.contains("Invalid number of values in line: ")) {
                    String failureMsg = "Failed to upload catalog,Invalid number of values in line";
                    emailDetails.setMsgBody(failureMsg);
                    emailDetails.setAttachment(fileName);
                    sendEmailNotification("meryembenbli4@gmail.com", "Failed to upload catalog.", failureMsg, fileName);
                    historyService.saveHistory("Upload failed", LocalDateTime.now(), emailDetails);
                    moveFile(fileName, FAILED_FOLDER);
                }
                else if (uploadResult.contains("wrong content: ")) {
                    String failureMsg = "Failed to upload catalog,Wrong content";
                    emailDetails.setMsgBody(failureMsg);
                    emailDetails.setAttachment(fileName);
                    sendEmailNotification("meryembenbli4@gmail.com", "Failed to upload catalog.", failureMsg, fileName);
                    historyService.saveHistory("Upload failed", LocalDateTime.now(), emailDetails);
                    moveFile(fileName, FAILED_FOLDER);
                }
            }
        } catch (IOException e) {
            logger.error("Error occurred while processing files: {}", e.getMessage());
            // Handle the exception as needed
        }
    }
    private void moveFile(String fileName, String destinationFolder) {
        File sourceFile = new File( fileName);
        File destinationFile = new File(destinationFolder + sourceFile.getName());
        if (sourceFile.renameTo(destinationFile)) {
            logger.info("File moved successfully to " + destinationFolder);
        } else {
            logger.error("Failed to move file to " + destinationFolder);

        }
    }

    // Check if the file has already been uploaded
    private boolean fileAlreadyUploaded(String fileName) {
        // Query the history table to check if the file name exists
        boolean fileNameExists = historyService.isFileUploaded(fileName);

        // Check if the subject exists in the history table
        boolean subjectExists = historyService.isSubjectUploaded(fileName);

        // Return true if either the file name or subject exists in the history table
        return fileNameExists || subjectExists;
    }

    private void sendFileAlreadyExistsNotification(String fileName) {
        String subject = "File Already Uploaded";
        String message = "The file " + fileName.substring(fileName.lastIndexOf("/") + 1) + " already exists in the system.";

        sendEmailNotification("meryembenbli4@gmail.com", subject, message, fileName);
    }

    private final GateeFile gateFile;

    @PostMapping("/uploadFiles")
    private String uploadFile(String path) {
        StringBuilder response = new StringBuilder();

        File file = new File(path);
        boolean isValidFile = true;

        response.append("File: ").append(file.getName()).append("\n"); // Corrected to use file.getName()

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",", -1);
                if (values.length != 34) { // Corrected length to include new columns
                    response.append("Invalid number of values in line: ").append(line).append("\n");
                    isValidFile = false;
                    break;
                }
            }
            if (isValidFile) {
                // Process the file only if it's valid
                reader.close();
                // Open a new reader to start from the beginning
                BufferedReader reader2 = new BufferedReader(new FileReader(file));
                while ((line = reader2.readLine()) != null) {
                    String[] values = line.split(",", -1);

                    // Create a Product object
                    Product product = new Product();

                    // Set the properties of the Product object from the values array
                    for (int i = 0; i < values.length; i++) {
                        switch (i) {
                            case 0 -> {
                                if (!(values[i] == null || values[i].equals(""))) {
                                    product.setProductId(values[i]);
                                }
                            }
                            case 1 -> {
                                if (!(values[i] == null || values[i].equals(""))) {
                                    product.setNature(values[i]);
                                }
                            }
                            case 2 -> {
                                if (!(values[i] == null || values[i].equals(""))) {
                                    product.setNomProduit(values[i]);
                                }
                            }
                            case 3 -> {
                                if (!(values[i] == null || values[i].equals(""))) {
                                    product.setTypeProduit(values[i]);
                                }
                            }
                            case 4 -> {
                                if (!(values[i] == null || values[i].equals(""))) {
                                    product.setFamilleOption(values[i]);
                                }
                            }
                            case 5 -> {
                                if (!(values[i] == null || values[i].equals(""))) {
                                    product.setTarifParDefaut(values[i]);
                                }
                            }
                            case 6 -> {
                                if (!(values[i] == null || values[i].equals(""))) {
                                    product.setTarifHT(Double.parseDouble(values[i]));
                                }
                            }
                            case 7 -> {
                                if (!(values[i] == null || values[i].equals(""))) {
                                    product.setTarifTTC(Double.parseDouble(values[i]));
                                }
                            }
                            case 8 -> {
                                if (!(values[i] == null || values[i].equals(""))) {
                                    product.setCoutDeRevient(Double.parseDouble(values[i]));
                                }
                            }
                            case 9 -> {
                                if (!(values[i] == null || values[i].equals(""))) {
                                    product.setMontant(Double.parseDouble(values[i]));
                                }
                            }
                            case 10 -> {
                                if (!(values[i] == null || values[i].equals(""))) {
                                    product.setOfferShdes(values[i]);
                                }
                            }
                            case 11 -> {
                                if (!(values[i] == null || values[i].equals(""))) {
                                    product.setOfferValue(values[i]);
                                }
                            }
                            case 12 -> {
                                if (!(values[i] == null || values[i].equals(""))) {
                                    product.setServiceShdes(values[i]);
                                }
                            }
                            case 13 -> {
                                if (!(values[i] == null || values[i].equals(""))) {
                                    product.setPackageShdes(values[i]);
                                }
                            }
                            case 14 -> {
                                if (!(values[i] == null || values[i].equals(""))) {
                                    product.setParameterShdes(values[i]);
                                }
                            }
                            case 15 -> {
                                if (!(values[i] == null || values[i].equals(""))) {
                                    product.setParameterValue(values[i]);
                                }
                            }
                            case 16 -> {
                                if (!(values[i] == null || values[i].equals(""))) {
                                    product.setParameterValueDes(values[i]);
                                }
                            }
                            case 17 -> {
                                if (!(values[i] == null || values[i].equals(""))) {
                                    product.setParameterNo(Integer.parseInt(values[i]));
                                }
                            }
                            case 18 -> {
                                if (!(values[i] == null || values[i].equals(""))) {
                                    product.setFaxIsMandatory(values[i]);
                                }
                            }
                            case 19 -> {
                                if (!(values[i] == null || values[i].equals(""))) {
                                    product.setApnIsMandatory(values[i]);
                                }
                            }
                            case 20 -> {
                                if (!(values[i] == null || values[i].equals(""))) {
                                    product.setCrmArpuOut(values[i]);
                                }
                            }
                            case 21 -> {
                                if (!(values[i] == null || values[i].equals(""))) {
                                    product.setCrmArpuIn(values[i]);
                                }
                            }
                            case 22 -> {
                                if (!(values[i] == null || values[i].equals(""))) {
                                    product.setCrmCoutDirect(values[i]);
                                }
                            }
                            case 23 -> {
                                if (!(values[i] == null || values[i].equals(""))) {
                                    product.setCrmCoutExploitation(values[i]);
                                }
                            }
                            case 24 -> {
                                if (!(values[i] == null || values[i].equals(""))) {
                                    product.setCrmCoutAcquisition(values[i]);
                                }
                            }
                            case 25 -> {
                                if (!(values[i] == null || values[i].equals(""))) {
                                    product.setCrmForfaitHtHorsRecharge(values[i]);
                                }
                            }
                            case 26 -> {
                                if (!(values[i] == null || values[i].equals(""))) {
                                    product.setCrmRecharge(values[i]);
                                }
                            }

                            case 27 -> {
                                if (!(values[i] == null || values[i].equals(""))) {
                                    product.setCrmBudgetSubvention12(values[i]);
                                }
                            }
                            case 28 -> {
                                if (!(values[i] == null || values[i].equals(""))) {
                                    product.setCrmBudgetSubvention24(values[i]);
                                }
                            }
                            case 29 -> {
                                if (!(values[i] == null || values[i].equals(""))) {
                                    product.setCrmBudgetSubvention36(values[i]);
                                }

                            }

                            case 30-> {
                                Catalog existingCatalog = catalogService.findByCatalogName(values[31]);
                                if (existingCatalog == null) {
                                    Catalog newCatalog = new Catalog();
                                    newCatalog.setId(Long.valueOf(values[30]));
                                    newCatalog.setCatalogName(values[31]);
                                    Long groupId = Long.valueOf(values[32]);
                                    Group group = groupService.findByGroupName(values[33]);
                                    if (group == null) {
                                        group = new Group();
                                        group.setNameG(values[33]);
                                        group.setId(Long.valueOf(values[32]));
                                        groupService.save(group);
                                        logger.error("Group with ID " + groupId + " does not exist.");
                                        newCatalog.setGroup(group);//i'm not sur ehere
                                    } else {
                                        newCatalog.setGroup(group);
                                    }
                                    catalogService.save(newCatalog);
                                    product.setCatalogId(newCatalog.getId());
                                } else {
                                    product.setCatalogId(existingCatalog.getId());
                                }
                            }


                            case 32-> {
                                Group group = groupService.findByGroupName(values[33]);
                                if (group == null) {
                                    group = new Group();
                                    group.setId(Long.valueOf(values[32]));
                                    group.setNameG(values[33]);
                                    groupService.save(group);
                                }

                                Catalog catalog = catalogService.findByCatalogName(values[31]);
                                if (catalog == null) {
                                    catalog = new Catalog();
                                    catalog.setId(Long.valueOf(values[30]));
                                    catalog.setCatalogName(values[31]);
                                    catalog.setGroup(group);
                                    catalogService.save(catalog);
                                } else {
                                    // If the catalog already exists, update its group
                                    catalog.setGroup(group);
                                    catalogService.save(catalog);
                                }
                                product.setCatalogId(catalog.getId());

                            }
                        }
                    }


                    // Persist the Product entity
                    productService.createProduct(product);
                }
                reader2.close();
                if(response.append((line)) != null){

                response.append("Inserted values: ").append(line).append("\n");}
                else {
                    response.append("wrong content: ").append(line).append("\n");
                }

            } else {
                response.append("Failed to upload file due to invalid structure.\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            response.append("Error reading file: ").append(e.getMessage()).append("\n");
        }

        logger.info(response.toString());
        return response.toString();
    }


    private List<String> getFileNamesFromFolder(String folderPath) throws IOException {
        List<String> fileNames = new ArrayList<>();
        File folder = new File(folderPath);

        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        fileNames.add(file.getAbsolutePath());
                    }
                }
            }
        } else {
            throw new IOException("Specified folder does not exist or is not a directory.");
        }

        return fileNames;
    }


    /* private void sendEmailNotification(String recipient, String subject, String attachmentPath) {
         EmailDetails emailDetails = new EmailDetails();
         emailDetails.setRecipient(recipient);
         emailDetails.setSubject(subject);
         emailDetails.setAttachment(attachmentPath);
         emailService.sendMailWithAttachment(emailDetails);
     }*/
    private void sendEmailNotification(String recipient, String subject, String message, String attachmentPath) {
        EmailDetails emailDetails = new EmailDetails();
        emailDetails.setRecipient(recipient);
        emailDetails.setSubject(subject);
        emailDetails.setMsgBody(message); // Set the message
        emailDetails.setAttachment(attachmentPath);
        emailService.sendMailWithAttachment(emailDetails);
    }


}
