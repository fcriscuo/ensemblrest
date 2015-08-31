package org.mskcc.cbio.reactive.subject;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

import org.apache.log4j.Logger;
import org.mskcc.cbio.vep.model.AnnotatorServiceMessage;
import rx.functions.Action1;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

import javax.annotation.Nullable;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by fcriscuo on 8/30/15.
 */
public enum EnsemblRestServiceSubject {
    INSTANCE;
    private final static Logger logger = Logger.getLogger(EnsemblRestServiceSubject.class);
    final PublishSubject<AnnotatorServiceMessage.AnnotationMessage> ensemblRestSubject = PublishSubject.create();
    private static final String ENSEMBL_URL_TEMPLATE = "http://grch37.rest.ensembl.org/vep/human/hgvs/GENPOS?";
    public Subject subject() { return this.ensemblRestSubject;}

    public void ensemblVepAnnotation(String variation, @Nullable String isoform) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(variation),
                "A variation is required");
        subject().onNext(AnnotatorServiceMessage.AnnotationMessage.create(variation, isoform,
                this.invokeEnsemblVepAnnotation(variation)));

    }

    private String invokeEnsemblVepAnnotation(String variation) {
        try {
            URL url = new URL(ENSEMBL_URL_TEMPLATE.replace("GENPOS",variation));
            URLConnection connection = url.openConnection();
            HttpURLConnection httpConnection = (HttpURLConnection) connection;
            httpConnection.setRequestProperty("Content-Type", "application/json");
            InputStream response = connection.getInputStream();
            int responseCode = httpConnection.getResponseCode();
            if(responseCode != 200) {
                if(responseCode == 429 && httpConnection.getHeaderField("Retry-After") != null) {
                    double sleepFloatingPoint = Double.valueOf(httpConnection.getHeaderField("Retry-After"));
                    double sleepMillis = 1000 * sleepFloatingPoint;
                    Thread.sleep((long)sleepMillis);
                    return invokeEnsemblVepAnnotation(variation);
                }
                logger.error("Response code was not 200. Detected response was " + responseCode +
                        " for genomic location: " + variation);
            }

            try(Reader reader  = new BufferedReader(new InputStreamReader(response, "UTF-8"))) {
                StringBuilder builder = new StringBuilder();
                char[] buffer = new char[8192];
                int read;
                while ((read = reader.read(buffer, 0, buffer.length)) > 0) {
                    builder.append(buffer, 0, read);
                }

                return builder.toString();

            } catch (IOException ex) {
                logger.error(ex.getMessage());
            }

        } catch ( InterruptedException |IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return "";
    }

    public static void main (String...args){
        EnsemblRestServiceSubject.INSTANCE.subject().subscribe(new Action1<AnnotatorServiceMessage.AnnotationMessage>() {
            @Override
            public void call(AnnotatorServiceMessage.AnnotationMessage message) {
                logger.info("====> variation " + message.hgvsVariation() +" annotation " +message.vepAnnotation());
            }
        });
        EnsemblRestServiceSubject.INSTANCE.ensemblVepAnnotation("17:g.41270062G>A",null);
        EnsemblRestServiceSubject.INSTANCE.ensemblVepAnnotation("10:g.42936755A>C",null);
        EnsemblRestServiceSubject.INSTANCE.ensemblVepAnnotation("7:g.140453999G>T",null);



    }


}
