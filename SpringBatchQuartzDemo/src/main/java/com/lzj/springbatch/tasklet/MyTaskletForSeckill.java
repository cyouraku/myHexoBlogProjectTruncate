package com.lzj.springbatch.tasklet;

import javax.sql.DataSource;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import com.lzj.mybatis.dao.SeckillMapper;
import com.lzj.springbatch.model.Seckill;

public class MyTaskletForSeckill implements Tasklet {

	private DataSource dataSource;

	private SeckillMapper seckillMapper;

	public SeckillMapper getSeckillMapper() {
		return seckillMapper;
	}

	public void setSeckillMapper(SeckillMapper seckillMapper) {
		this.seckillMapper = seckillMapper;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		Seckill seckill = new Seckill();
		seckill = seckillMapper.queryById(1L);
		System.out.println(seckill);
		return RepeatStatus.FINISHED;
	}

}
