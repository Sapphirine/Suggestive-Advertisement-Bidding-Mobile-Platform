package Mahout_Test_1.Test_1;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

/**
 * Hello world!
 *
 */
    public class App
    {
        public static void main( String[] args ) throws IOException, TasteException
        {
            // DataModel model = new FileDataModel(new File("data.csv"));
            // DataModel model = new FileDataModel(new File("xac.txt"));
             DataModel model = new FileDataModel(new File("advertiser-bid-test.txt"));
                
            // Create a buffer reader to massage the data ydata-ysm-advertiser-bids-v1_0.txt, which is obtained from Yahoo Labs.
            // Only need to run this once to clean the data into the following format
            // ACCOUNT_ID, PHRASE_ID, PRICE
            /*
             BufferedReader br = new BufferedReader(new FileReader("ydata-ysm-advertiser-bids-v1_0.txt"));
             BufferedWriter bw = new BufferedWriter(new FileWriter("advertiser-bid-test.txt"));
             
             String line;
             
             while((line = br.readLine()) != null){
                 
                 String[] values = line.split("\\t",-1);
                 bw.write(values[2] + "," + values[1] + "," + values[3] + "\n");
             }
             br.close();
             bw.close();
             */
            //DataModel model = new FileDataModel(new File("ydata-ysm-advertiser-bids-v1_0.txt"));
                   
           
            RecommenderBuilder builder = new RecommenderBuilder() {
                public Recommender buildRecommender(DataModel model) throws TasteException{
                   
                    UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
                    UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, similarity, model);
                   
                    return new GenericUserBasedRecommender(model, neighborhood, similarity);
                }
            };
           
            // User Similarity
            UserSimilarity similarity_User = new PearsonCorrelationSimilarity(model);
                       
            //Item Similarity       
            ItemSimilarity similarity_Item = new PearsonCorrelationSimilarity(model);
            //ALS Similarity
                           
            UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, similarity_User, model);
            UserBasedRecommender recommender_User = new GenericUserBasedRecommender(model, neighborhood, similarity_User);
                           
            GenericItemBasedRecommender recommender_Item = new GenericItemBasedRecommender(model, similarity_Item);
           
        //  RecommenderEvaluator evaluator = new AverageAbsoluteDifferenceRecommenderEvaluator();
        //  double result = evaluator.evaluate(builder, null, model, 0.9, 1.0);
        //  System.out.println(result);
               List<RecommendedItem> recommendations_Items_List = recommender_Item.mostSimilarItems(39, 5);
        //    List<RecommendedItem> recommendations_Users_List = recommender_User.recommend(691, 3);
                   
        //    List recommendations_Items_List = recommender_User.recommend(691, 3);
           
            for (RecommendedItem recommendation_Item : recommendations_Items_List)
            {
                      System.out.println( recommendation_Item );
            }
        }
    }