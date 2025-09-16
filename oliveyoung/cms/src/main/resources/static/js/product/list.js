document.addEventListener('DOMContentLoaded', () => {
    productList(); // DOM이 다 생성된 후 호출

    eventBind();
});


// 상품 데이터 조회
function productList(page = 0) {
    const PAGE_SIZE = 10;
    const searchType = document.getElementById('searchType').value ?? '';
    const searchKeyword = document.getElementById('searchKeyword').value ?? '';

    let reqData = {
        size : PAGE_SIZE,
        page : page,
        searchType : searchType,
        searchKeyword : searchKeyword
    };

    httpUtil.get(`/cms/api/product`, reqData
    ).then(result => {
        let totalElements = result.totalElements;
        let totalPages = result.totalPages;
        let isLast = result.isLast;
        let isFirst = result.isFirst;

        const tbody = document.getElementById("mainContent");
        tbody.innerHTML = result.content
            .map(product => makeProductListHTML(product))
            .join("");

        // 페이지네이션 갱신
        renderPagination(page, PAGE_SIZE, totalPages, totalElements, isLast, isFirst);

    }).catch(err => {
        console.error('GET 실패:', err);
    });
}

// 상품 테이블 row data 만드는 HTML
function makeProductListHTML(data) {
    let productId = data.id;
    let productCode = data.productCode;
    let productName = data.productName;
    let brandName = data.brandName;
    let categoryName = data.categoryName;
    let productStatus = data.productStatus;

    return `
        <tr>
            <td class="text-center"><img src="../../../dist/img/user1.svg" alt="user" class="rounded-circle" width="40"></td>
            <td>${productName}</td>
            <td class="text-center">${productCode}</td>
            <td class="text-center">0</td>
            <td class="text-center">${brandName}</td>
            <td class="text-center">${categoryName}</td>
            <td class="text-center"><span class="badge badge-pill badge-secondary">${productStatus}</span></td>
            <td class="text-center">
            <div class="btn-group btn-group-sm" role="group">
                <a href="javascript:void(0)" class="btn btn-link btn-icon bigger-130 text-primary" title="Detail"><i class="material-icons">visibility</i></a>
                <a href="javascript:void(0)" class="btn btn-link btn-icon bigger-130 text-success" title="Edit"><i class="material-icons">edit</i></a>
                <a href="javascript:void(0)" class="btn btn-link btn-icon bigger-130 text-danger" title="Delete"><i class="material-icons">delete_outline</i></a>
            </div> 
            </td>
        </tr>
    `;
}


// 페이지 네이션 적용하기
function renderPagination(currentPage, pageSize, totalPages, totalElements, isLast, isFirst){
    const pagination = document.querySelector('.pagination');
    pagination.innerHTML = '';

    // 이전 버튼
    const prevDisabled = isFirst ? 'disabled' : '';
    pagination.innerHTML += `
        <li class="page-item ${prevDisabled}">
            <a class="page-link has-icon" href="javascript:void(0)" onclick="productList(${currentPage - 1})">
                <i class="material-icons">chevron_left</i>
            </a>
        </li>
    `;

    // 페이지 번호
    let startPage = Math.floor(currentPage / pageSize) * pageSize;
    let endPage = Math.min(startPage + 9, totalPages - 1);
    for (let i = startPage; i <= endPage; i++) {
        if (i === currentPage) {
            pagination.innerHTML += `
                <li class="page-item active">
                    <span class="page-link">${i + 1}</span>
                </li>
            `;
        } else {
            pagination.innerHTML += `
                <li class="page-item">
                    <a class="page-link" href="javascript:void(0)" onclick="productList(${i})">${i + 1}</a>
                </li>
            `;
        }
    }

    // 다음 버튼
    const nextDisabled = isLast ? "disabled" : "";
    pagination.innerHTML += `
        <li class="page-item ${nextDisabled}">
            <a class="page-link has-icon" href="javascript:void(0)" onclick="productList(${currentPage + 1})">
                <i class="material-icons">chevron_right</i>
            </a>
        </li>
    `;
}

function eventBind(){
    const searchBtn = document.getElementById('productSearchBtn');
    searchBtn.addEventListener('click', () => {
        productList();
    });
}