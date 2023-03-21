package com.qmt.besedo.service.search;

import com.qmt.besedo.model.message.MessageAttributeName;
import com.qmt.besedo.model.operator.SearchOperator;
import com.qmt.besedo.model.response.Response;
import com.qmt.besedo.repository.MessageDao;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static com.qmt.besedo.response.Responses.buildResponseWithResultsFromExecution;

@RequiredArgsConstructor
@Service
public class SearchMessageServiceImpl implements SearchMessageService {

    private final MessageDao messageDao;

    @Override
    public ResponseEntity<Response> getMessages(MessageAttributeName messageAttributeName,
                                                SearchOperator searchOperator,
                                                String filterValue,
                                                Pageable pageable) {
        return buildResponseWithResultsFromExecution("query is successful",
                HttpStatus.OK,
                "Error when getting messages, please contact your administrator",
                HttpStatus.INTERNAL_SERVER_ERROR,
                messageDao.getMessageByAttribute(messageAttributeName,
                        searchOperator,
                        filterValue, pageable));
    }

}
