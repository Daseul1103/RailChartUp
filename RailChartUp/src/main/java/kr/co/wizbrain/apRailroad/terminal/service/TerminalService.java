package kr.co.wizbrain.apRailroad.terminal.service;

import java.util.List;

import kr.co.wizbrain.apRailroad.eventLog.vo.LogVO;
import kr.co.wizbrain.apRailroad.terminal.vo.DirectRegularVO;
import kr.co.wizbrain.apRailroad.terminal.vo.TerminalVO;

public interface TerminalService {

	public List<TerminalVO> selectTerminalList(TerminalVO tvo);

	public void terminalInsert(TerminalVO terminalVO);

	public TerminalVO selectTerminal(TerminalVO terminalVO);

	public void terminalUpdate(TerminalVO terminalVO);

	public void terminalDelete(List<String> terminalArr);

	public List<DirectRegularVO> selectDrtRgr(DirectRegularVO tvo);

	public void drtRgrUpdate(DirectRegularVO dPreVo, DirectRegularVO drVo, LogVO lvo);
	
}
