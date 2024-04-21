package com.ftp.osmserverproj;
import com.ftp.osmserverproj.Config.GateeFile;
import com.ftp.osmserverproj.Model.EmailDetails;
import com.ftp.osmserverproj.Model.Product;
import com.ftp.osmserverproj.Service.EmailService;
import com.ftp.osmserverproj.Service.HistoryService;
import com.ftp.osmserverproj.Service.ProductService;
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

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    @Autowired
    private final ProductService productService;
    @Autowired
    private  EmailService emailService;
    @Autowired
    private HistoryService historyService;


    public ScheduledTasks(ProductService productService, GateeFile gateFile) {
        this.productService = productService;
        this.gateFile = gateFile;
    }

    @Scheduled(cron = "0 */1 * * * ?") //0 8-18/4 * * *  (from 8am to 6pm every 4 hour)


    /*public void scheduleTaskWithCronExpression() {
        logger.info("Cron Task :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
        //uploadFiles();
        String result = uploadFiles();
        EmailDetails emailDetails = new EmailDetails();
        emailDetails.setRecipient("meryembenbli4@gmail.com");
        //String attachmentPath = "C:/apache-ftpserver-1.1.3-bin/res/home/catTest.txt"; // catalog wrong file : catTest.txt
        String attachmentPath = "C:/apache-ftpserver-1.1.3-bin/res/home/";

        emailDetails.setSubject("Uploaded Files");
        // Check if the upload was successful
        if (result.contains("Inserted values")) {
            emailDetails.setMsgBody("Catalog uploaded successfully.");
            //sendEmailNotification("meryembenbli4@gmail.com", filename,"Catalog uploaded successfully.", attachmentPath);

            sendEmailNotification("meryembenbli4@gmail.com", "Catalog uploaded successfully.", attachmentPath);
            historyService.saveHistory("Upload successful", LocalDateTime.now(), emailDetails);
        } else if (result.contains("Invalid number of values in line: ")) {
            emailDetails.setMsgBody("Failed to upload catalog.");
            emailDetails.setAttachment(attachmentPath);
            //sendEmailNotification("meryembenbli4@gmail.com", filename,"Failed to upload catalog.", attachmentPath);
            sendEmailNotification("meryembenbli4@gmail.com", "Failed to upload catalog.", attachmentPath);
            historyService.saveHistory("Upload failed", LocalDateTime.now(), emailDetails);
        }
    }*/
    public void uploadFilesAndSendEmails() {
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
    }
    private final GateeFile gateFile;
    @PostMapping("/uploadFiles")
   /* private String uploadFiles() {
        StringBuilder response = new StringBuilder();
        List<File> files = gateFile.mget("C:/apache-ftpserver-1.1.3-bin/res/home");
        if (files.isEmpty()) {
            response.append("No files found in the specified path.").append("\n");
        } else {

            response.append("Found files in the specified path:").append("\n");
            for (File file : files) {
                boolean isValidFile = true;
                response.append("File: ").append(file.getAbsolutePath()).append("\n");
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] values = line.split(",", -1);
                        if (values.length != 30) {
                            response.append("Invalid number of values in line: ").append(line).append("\n");
                            isValidFile = false;
                            break;
                        }
                        Product product = new Product();
                        for (int i = 0; i < values.length; i++) {
                            if (!(values[i] == null || values[i].equals(""))) {
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

                                }
                            }
                        }
                        productService.createProduct(product);
                        response.append("Inserted values: ").append(line).append("\n");
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    response.append("Error reading file: ").append(e.getMessage()).append("\n");
                }
            }
        }
        logger.info(response.toString());
        return response.toString();
    }*/
    /*final
    private String uploadFiles() {

        StringBuilder response = new StringBuilder();

        // Get the names of files in the specified folder
        try {
            List<String> fileNames = getFileNamesFromFolder("C:/apache-ftpserver-1.1.3-bin/res/home");

            if (fileNames.isEmpty()) {
                response.append("No files found in the specified path.").append("\n");
            } else {
                response.append("Found files in the specified path:").append("\n");

                // Iterate over the file names and process each file
                for (String fileName : fileNames) {
                    File file = new File(fileName);
                    boolean isValidFile = true;

                    response.append("File: ").append(fileName).append("\n");

                    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                        String line;

                        while ((line = reader.readLine()) != null) {
                            String[] values = line.split(",", -1);

                            if (values.length != 30) {
                                response.append("Invalid number of values in line: ").append(line).append("\n");
                                isValidFile = false;
                                break;
                            }

                            Product product = new Product();

                            for (int i = 0; i < values.length; i++) {
                                if (!(values[i] == null || values[i].equals(""))) {
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
                                    }
                                }
                            }

                            productService.createProduct(product);
                            response.append("Inserted values: ").append(line).append("\n");
                            //add map Map
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        response.append("Error reading file: ").append(e.getMessage()).append("\n");
                    }
                }//for
            }
        } catch (IOException e) {
            e.printStackTrace();
            response.append("Error retrieving file names: ").append(e.getMessage()).append("\n");
        }

        logger.info(response.toString());
        return response.toString();
    }*/
    private String uploadFile(String path) {
        StringBuilder response = new StringBuilder();

        File file = new File(path);
        boolean isValidFile = true;

        response.append("File: ").append(file.getName()).append("\n"); // Corrected to use file.getName()

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",", -1);

                if (values.length != 30) {
                    response.append("Invalid number of values in line: ").append(line).append("\n");
                    isValidFile = false;
                    break;
                }

                Product product = new Product();

                for (int i = 0; i < values.length; i++) {
                    if (!(values[i] == null || values[i].equals(""))) {
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
                        }
                    }
                }

                productService.createProduct(product);
                response.append("Inserted values: ").append(line).append("\n");
                //add map Map
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

    /*@PostMapping("/sendEmailNotification")
    public String sendEmailNotification(@RequestBody EmailNotificationRequest request) {
        // Assuming EmailNotificationRequest contains recipient, subject, and message fields
        sendEmailNotification(request.getRecipient(), request.getSubject(), request.getMessage());
        return "Email notification sent successfully";
    }*/

    private void sendEmailNotification(String recipient, String subject, String attachmentPath) {
        EmailDetails emailDetails = new EmailDetails();
        emailDetails.setRecipient(recipient);
        emailDetails.setSubject(subject);
        emailDetails.setAttachment(attachmentPath);
        emailService.sendMailWithAttachment(emailDetails);
    }




}
