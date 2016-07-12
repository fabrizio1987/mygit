package rates.batch;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.batch.item.ItemProcessor;

import rates.entity.Rate;

public class RateItemProcessor implements ItemProcessor<RateLine, Rate> {

    @Override
    public Rate process(final RateLine rateLine) throws Exception {

        final Rate transformedRate = new Rate();
        
        BigDecimal r = new BigDecimal(rateLine.getRate());
        r = r.setScale(7);
        BigDecimal divisor = new BigDecimal(100000);
        transformedRate.setRate(r.divide(divisor, RoundingMode.HALF_UP));
        
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        Date validDate = sf.parse(rateLine.getDate());
        transformedRate.setValidDate(validDate);
        
        transformedRate.setBuyCurrency(rateLine.getBuy());
        transformedRate.setSellCurrency(rateLine.getSell());

        System.out.println("Converting (" + rateLine + ") into (" + transformedRate + ")");

        return transformedRate;
    }

}
