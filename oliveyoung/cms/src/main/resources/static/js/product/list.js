productList();


// 상품 데이터 조회
function productList(page = 0) {
    const PAGE_SIZE = 10;

    let reqData = {
        size : PAGE_SIZE,
        page : page
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