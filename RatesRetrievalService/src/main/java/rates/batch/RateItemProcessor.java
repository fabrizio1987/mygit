package rates.batch;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.batch.item.ItemProcessor;

import rates.entity.Rate;

public class RateItemProcessor implements ItemProcessor<RateLine, Rate> {

    @Override
    public Rate process(final RateLine rateLine) throws Exception {
//        final String firstName = person.getFirstName().toUpperCase();
//        final String lastName = person.getLastName().toUpperCase();

        final Rate transformedRate = new Rate();
        
        transformedRate.setRate(new BigDecimal(15));
        transformedRate.setValidDate(new Date());

        System.out.println("Converting (" + rateLine + ") into (" + transformedRate + ")");

        return transformedRate;
    }

}
