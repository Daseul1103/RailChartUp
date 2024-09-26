package kr.co.wizbrain.apRailroad.terminal.mapper;

import java.util.HashMap;
import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import kr.co.wizbrain.apRailroad.comm.KeyMapVo;
import kr.co.wizbrain.apRailroad.eventLog.vo.EventLogVO;
import kr.co.wizbrain.apRailroad.terminal.vo.DirectRegularVO;
import kr.co.wizbrain.apRailroad.terminal.vo.TerminalVO;

@Mapper("terminalMapper")
public interface TerminalMapper {

	public List<TerminalVO> selectTerminal(TerminalVO tvo);

	public void terminalInsert(TerminalVO terminalVO);

	public void terminalUpdate(TerminalVO terminalVO);

	public void terminalDelete(HashMap<String, Object> map);
	
	public void idxAutoIncrement();

	public List<DirectRegularVO> selectDrtRgr(DirectRegularVO tvo);

	public void drtRgrUpdate(DirectRegularVO tvo);
	
	public List<KeyMapVo> findComm(HashMap<String, Object> map);

	public void insertDREvtLog(EventLogVO evo);
}
