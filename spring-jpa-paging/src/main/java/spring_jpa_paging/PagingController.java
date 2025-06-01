package spring_jpa_paging;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PagingController {

    private final PagingRepository pagingRepository;

    @GetMapping("/page")
    public Page<Member> getPage() {
        return pagingRepository.findPageBy(PageRequest.of(0, 5));
    }

    @GetMapping("/slice")
    public Slice<Member> getSlice() {
        return pagingRepository.findSliceBy(PageRequest.of(0, 5));
    }
}
