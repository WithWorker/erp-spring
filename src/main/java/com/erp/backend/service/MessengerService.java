package com.erp.backend.service;

import com.erp.backend.model.FileVO;
import com.erp.backend.model.InterMessengerDao;
import com.erp.backend.model.MessengerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class MessengerService implements InterMessengerService {

    private final InterMessengerDao imDao;

    @Autowired
    public MessengerService(@Qualifier("messengerDao") InterMessengerDao imDao) {  // ✅ 명확하게 지정
        this.imDao = imDao;
    }

    // 전체 부서 조회
    @Override
    public List<Map<String, Object>> getDept() {
        return imDao.getDept();
    }

    // 부서 직원 조회
    @Override
    public List<Map<String, Object>> getDeptPerson(Map<String, Object> map) {
        return imDao.getDeptPerson(map);
    }

    // 선택 직원 조회
    @Override
    public List<Map<String, Object>> getChosenEmp(Long empId) {
        return imDao.getChosenEmp(empId);
    }

    // 메신저 보내기
    @Override
    public void sendMessage(MessengerVO msgvo) {
        if (msgvo == null || msgvo.getSenderId() == null || msgvo.getReceiverId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "❌ senderId 또는 receiverId가 필요합니다.");
        }

        // senderId와 receiverId가 employee 테이블에 존재하는지 확인
        boolean isValidSender = imDao.isEmployeeExists(msgvo.getSenderId());
        boolean isValidReceiver = imDao.isEmployeeExists(msgvo.getReceiverId());

        if (!isValidSender || !isValidReceiver) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "❌ senderId 또는 receiverId가 유효하지 않습니다.");
        }

        try {
            imDao.sendMessage(msgvo);
            System.out.println("✅ 메시지 전송 완료: msgId=" + msgvo.getMessengerId());
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "🚨 데이터 무결성 오류: senderId 또는 receiverId가 employee 테이블에 존재하지 않습니다.");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "🚨 서버 오류 발생: " + e.getMessage());
        }
    }

    // 메신저 전달
    @Override
    public void deliverMessage(MessengerVO originalMessage, MessengerVO newMessage) {
        if (originalMessage == null || newMessage == null) {
            throw new IllegalArgumentException("❌ 전달할 메시지 정보가 필요합니다.");
        }

        // ✅ senderId와 receiverId가 존재하는지 확인
        if (!imDao.isEmployeeExists(newMessage.getSenderId()) || !imDao.isEmployeeExists(newMessage.getReceiverId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "❌ 존재하지 않는 senderId 또는 receiverId 입니다.");
        }

        // ✅ 기존 메시지가 존재하는지 확인
        if (!imDao.isMessageExists(originalMessage.getMessengerId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "❌ 전달할 메시지가 존재하지 않습니다.");
        }

        // ✅ 메시지 전달
        try {
            imDao.sendMessage(newMessage);
            System.out.println("✅ 메시지 전달 완료: msgId=" + newMessage.getMessengerId());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "🚨 메시지 전달 실패: " + e.getMessage());
        }
    }

    // 기존 메시지 가져오기
    @Override
    public MessengerVO getMessageById(Long msgId) {
        return imDao.getMessageById(msgId);
    }

    // 보낸 메시지 리스트
    @Override
    public List<Map<String, Object>> getSendMsg(Map<String, Object> paramMap) {
        return imDao.getSendMsg(paramMap);
    }

    // 받은 메시지 리스트
    @Override
    public List<Map<String, Object>> getReceivedMsg(Map<String, Object> paramMap) {
        return imDao.getReceivedMsg(paramMap);
    }


    // 보낸 메신저 내용 조회
    @Override
    public Map<String, Object> getMsgContent(Long msgId, Long senderId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("messageId", msgId);
        paramMap.put("senderId", senderId);

        System.out.println("🔍 실행할 쿼리 파라미터: " + paramMap);

        Map<String, Object> result = imDao.getMsgContent(paramMap);

        if (result == null || result.isEmpty()) {
            System.out.println("🚨 MyBatis에서 결과 없음! msgId: " + msgId + ", senderId: " + senderId);
            return Collections.emptyMap();
        }

        System.out.println("✅ 조회 결과: " + result);
        return result;
    }

    // 받은 메신저 내용 조회
    public Map<String, Object> getMsgContent2(Long msgId, Long receiverId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("messageId", msgId);
        paramMap.put("receiverId", receiverId);

        System.out.println("🔍 실행할 쿼리 파라미터: " + paramMap);

        Map<String, Object> result = imDao.getMsgContent2(paramMap);

        if (result == null || result.isEmpty()) {
            System.out.println("🚨 MyBatis에서 결과 없음! msgId: " + msgId + ", receiverId: " + receiverId);
            return Collections.emptyMap();
        }

        System.out.println("✅ 조회 결과: " + result);
        return result;
    }

    // 안읽은 메신저 읽기
    @Override
    public void updateAllMsg(Long empId) {
        if (empId == null) {
            throw new IllegalArgumentException("❌ empId가 NULL입니다.");
        }

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("empId", empId);

        imDao.updateAllMsg(paramMap);
    }

    // 첨부파일 추가
    @Override
    public void addFile(FileVO fileVO) {
        if (fileVO.getMessengerId() == null || fileVO.getFilePath() == null) {
            throw new IllegalArgumentException("❌ MessengerId와 filePath는 필수입니다.");
        }

        // 파일명 추출 (경로에서 마지막 "/" 이후 값)
        String fileName = fileVO.getFilePath().substring(fileVO.getFilePath().lastIndexOf("/") + 1);
        fileVO.setFileName(fileName);

        imDao.addFile(fileVO);
        System.out.println("✅ 파일 추가 완료: " + fileVO);
    }

    // 첨부파일 조회
    @Override
    public List<FileVO> getMsgFile(Long msgId) {
        if (msgId == null) {
            throw new IllegalArgumentException("❌ msgId가 필요합니다.");
        }

        List<FileVO> fileList = imDao.getMsgFile(msgId);
        if (fileList == null || fileList.isEmpty()) {
            System.out.println("🚨 첨부파일 없음! msgId=" + msgId);
            return List.of();
        }

        System.out.println("✅ 조회된 파일 목록: " + fileList);
        return fileList;
    }

    // 메신저 방 개수
    @Override
    public int getTotalMsg(Map<String, String> map) {
        return imDao.getTotalMsg(map);
    }

    // 메시지 발송을 위한 사람 이름 조회
    @Override
    public Map<String, Object> getEmpName(Long receiverId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("receiverId", receiverId);

        System.out.println("🔍 수신자 조회 실행: " + paramMap);

        Map<String, Object> result = imDao.getEmpName(paramMap);

        if (result == null || result.isEmpty()) {
            System.out.println("🚨 조회 결과 없음! receiverId: " + receiverId);
            return Collections.emptyMap();
        }

        System.out.println("✅ 수신자 정보 조회 성공: " + result);
        return result;
    }

    // 안읽은 메신저 개수
    @Override
    public int getUnreadMsg(Long empId) {
        return imDao.getUnreadMsg(Map.of("empId", empId));
    }

    // 메신저 방 삭제
    @Override
    public boolean deleteMessage(Long msgId, Long empId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("msgId", msgId);
        paramMap.put("empId", empId);

        int result = imDao.deleteMessage(paramMap);
        return result > 0;
    }


}