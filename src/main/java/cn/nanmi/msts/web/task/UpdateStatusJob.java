package cn.nanmi.msts.web.task;

import cn.nanmi.msts.web.business.IStockBusiness;
import cn.nanmi.msts.web.service.IStockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class UpdateStatusJob {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	private IStockBusiness stockBusiness ;
	
	@Scheduled(cron="0 0 2 * * ?")
	public void updateStatus(){
		try {
			LOGGER.info("开始更新状态...");
			stockBusiness.updateOrderState();
			LOGGER.info("更新状态成功!");
		} catch (Exception e) {
			LOGGER.debug(e.toString());
		}
		

	}
}
