import{F as W,r as o,j as e,H as a,g as D,c as H,u as U,a as X,M as q,ab as G,ae as C,B as J,al as K,ah as Q,af as Y,W as Z,m as L,X as y}from"./index-CclgMCKg.js";import{d as A}from"./index-317k7Gzh.js";import{B as k}from"./BottomSheet-nkPRIX3f.js";import{S as ee,a as le}from"./swiper-react-BiZMhr8u.js";import{R as se}from"./index-coMdXwgz.js";const te=W({key:"editReview",default:{userReview:null,reviewReply:""}}),ae=({imgArr:l,reviewId:s})=>{const c=l==null?void 0:l.length,[d,i]=o.useState(!1),x=o.useRef(null),t=()=>{i(!0)},p=()=>{i(!1)};return e.jsxs("div",{children:[c===1&&e.jsx("ul",{className:"h-[400px] rounded-lg overflow-hidden",children:e.jsx("li",{className:"w-full h-full bg-slate-200",children:e.jsx("img",{src:`${a}/${s}/${l[0].pic??l[0].title}`,alt:`${s}`,ref:x,className:"w-full h-full object-cover cursor-pointer",onClick:t})})}),c===2&&e.jsxs("ul",{className:"h-[400px] grid grid-cols-2 gap-[10px] rounded-lg overflow-hidden",children:[e.jsx("li",{className:"w-full h-full bg-slate-200",children:e.jsx("img",{src:`${a}/${s}/${l[0].pic??l[0].title}`,alt:`${l[0].reviewId}`,className:"w-full h-full object-cover cursor-pointer",onClick:t})}),e.jsx("li",{className:"w-full h-full bg-slate-200",children:e.jsx("img",{src:`${a}/${s}/${l[1].pic??l[1].title}`,alt:`${l[1].reviewId}`,className:"w-full h-full object-cover cursor-pointer",onClick:t})})]}),c===3&&e.jsxs("ul",{className:"h-[400px] grid grid-cols-4 grid-rows-4 gap-[10px] rounded-lg overflow-hidden",children:[e.jsx("li",{className:"bg-slate-200 col-span-2 row-span-4",children:e.jsx("img",{className:"w-full h-full object-cover cursor-pointer",src:`${a}/${s}/${l[0].pic??l[0].title}`,alt:`${l[0].reviewId}`,onClick:t})}),e.jsx("li",{className:"bg-slate-200 col-span-2 row-span-2 col-start-3 row-start-1",children:e.jsx("img",{className:"w-full h-full object-cover cursor-pointer",src:`${a}/${s}/${l[1].pic??l[1].title}`,alt:`${l[1].reviewId}`,onClick:t})}),e.jsx("li",{className:"bg-slate-200 col-span-2 row-span-2 col-start-3 row-start-3",children:e.jsx("img",{className:"w-full h-full object-cover cursor-pointer",src:`${a}/${s}/${l[2].pic??l[2].title}`,alt:`${l[2].reviewId}`,onClick:t})})]}),c===4&&e.jsxs("ul",{className:"h-[400px] grid grid-cols-4 grid-rows-4 gap-2.5 rounded-lg overflow-hidden",children:[e.jsx("li",{className:"bg-slate-200 col-start-3 col-end-5 row-start-1 row-end-3",children:e.jsx("img",{className:"w-full h-full object-cover cursor-pointer",src:`${a}/${s}/${l[0].pic??l[0].title}`,alt:`${l[0].reviewId}`,onClick:t})}),e.jsx("li",{className:"bg-slate-200 col-start-3 col-end-5 row-start-3 row-end-5",children:e.jsx("img",{className:"w-full h-full object-cover cursor-pointer",src:`${a}/${s}/${l[1].pic??l[1].title}`,alt:`${l[1].reviewId}`,onClick:t})}),e.jsx("li",{className:"bg-slate-200 col-start-1 col-end-3 row-start-1 row-end-3",children:e.jsx("img",{className:"w-full h-full object-cover cursor-pointer",src:`${a}/${s}/${l[2].pic??l[2].title}`,alt:`${l[2].reviewId}`,onClick:t})}),e.jsx("li",{className:"bg-slate-200 col-start-1 col-end-3 row-start-3 row-end-5",children:e.jsx("img",{className:"w-full h-full object-cover cursor-pointer",src:`${a}/${s}/${l[3].pic??l[3].title}`,alt:`${l[3].reviewId}`,onClick:t})})]}),c===5&&e.jsxs("ul",{className:"h-[400px] grid grid-cols-4 grid-rows-4 gap-2.5 rounded-lg overflow-hidden",children:[e.jsx("li",{className:"col-start-1 col-end-3 row-start-1 row-end-5 bg-slate-200",children:e.jsx("img",{className:"w-full h-full object-cover cursor-pointer",src:`${a}/${s}/${l[0].pic??l[0].title}`,alt:`${l[0].reviewId}`,onClick:t})}),e.jsx("li",{className:"col-start-3 col-end-4 row-start-1 row-end-3 bg-slate-200",children:e.jsx("img",{className:"w-full h-full object-cover cursor-pointer",src:`${a}/${s}/${l[1].pic??l[1].title}`,alt:`${l[1].reviewId}`,onClick:t})}),e.jsx("li",{className:"col-start-3 col-end-4 row-start-3 row-end-5 bg-slate-200",children:e.jsx("img",{className:"w-full h-full object-cover cursor-pointer",src:`${a}/${s}/${l[2].pic??l[2].title}`,alt:`${l[2].reviewId}`,onClick:t})}),e.jsx("li",{className:"col-start-4 col-end-5 row-start-1 row-end-3 bg-slate-200",children:e.jsx("img",{className:"w-full h-full object-cover cursor-pointer",src:`${a}/${s}/${l[3].pic??l[3].title}`,alt:`${l[3].reviewId}`,onClick:t})}),e.jsx("li",{className:"col-start-4 col-end-5 row-start-3 row-end-5 bg-slate-200",children:e.jsx("img",{className:"w-full h-full object-cover cursor-pointer",src:`${a}/${s}/${l[4].pic??l[4].title}`,alt:`${l[4].reviewId}`,onClick:t})})]}),c>5&&e.jsxs("ul",{className:"h-[400px] grid grid-cols-4 grid-rows-4 gap-2.5 rounded-lg overflow-hidden",children:[e.jsx("li",{className:"col-start-1 col-end-3 row-start-1 row-end-5 bg-slate-200",children:e.jsx("img",{className:"w-full h-full object-cover cursor-pointer",src:`${a}/${s}/${l[0].pic??l[0].title}`,alt:`${l[0].reviewId}`,onClick:t})}),e.jsx("li",{className:"col-start-3 col-end-4 row-start-1 row-end-3 bg-slate-200",children:e.jsx("img",{className:"w-full h-full object-cover cursor-pointer",src:`${a}/${s}/${l[1].pic??l[1].title}`,alt:`${l[1].reviewId}`,onClick:t})}),e.jsx("li",{className:"col-start-3 col-end-4 row-start-3 row-end-5 bg-slate-200",children:e.jsx("img",{className:"w-full h-full object-cover cursor-pointer",src:`${a}/${s}/${l[2].pic??l[2].title}`,alt:`${l[2].reviewId}`,onClick:t})}),e.jsx("li",{className:"col-start-4 col-end-5 row-start-1 row-end-3 bg-slate-200",children:e.jsx("img",{className:"w-full h-full object-cover cursor-pointer",src:`${a}/${s}/${l[3].pic??l[3].title}`,alt:`${l[3].reviewId}`,onClick:t})}),e.jsxs("li",{className:"col-start-4 col-end-5 row-start-3 row-end-5 bg-slate-200 relative rounded-lg",children:[e.jsx("img",{className:"w-full h-full object-cover cursor-pointer",src:`${a}/${s}/${l[4].pic??l[4].title}`,alt:`${l[4].reviewId}`,onClick:t}),e.jsxs("div",{className:"absolute inset-0 bg-black bg-opacity-50 content-[''] z-10 flex items-center justify-center text-white rounded-lg",children:["+ ",c-5]})]})]}),d&&e.jsx("div",{className:"fixed max-w-3xl w-full mx-auto h-screen top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 flex flex-col px-[32px] py-[30px] gap-[30px] bg-black bg-opacity-50 z-50",onClick:p,children:e.jsx("div",{className:"max-w-3xl w-full absolute top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 flex justify-center items-center px-[32px] bg-slate-900",children:e.jsx(ee,{slidesPerView:1,spaceBetween:10,loop:!0,className:"mySwiper h-[406px] overflow-hidden ",onClick:n=>n.stopPropagation(),children:l.map((n,u)=>e.jsx(le,{children:e.jsx("div",{className:"w-full h-full ",children:e.jsx("img",{src:`${a}/${s}/${n.pic??n.title}`,alt:`${n.reviewId}`,className:"w-full h-full object-cover"})})},u))})})})]})},xe=({strfId:l,item:s,getReviewList:c})=>{const d=D("accessToken"),[i]=H(),x=i.get("type"),t=U(),p=()=>{t(`/business/review/edit?type=create&strfId=${l}&reviewId=${s.reviewId}`)},n=()=>{t(`/business/review/edit?type=edit&strfId=${l}&reviewId=${s.reviewId}&replayId=${s.reviewReplyId}`)},u=()=>{t(`/report?type=${Z.REVIEW}&reportTarget=${s.reviewId}`)},$=X().pathname,[v,b]=q(te),[h,R]=o.useState(!1),[I,S]=o.useState(!1),[N,r]=o.useState(!1),[B,m]=o.useState(!1),[P,f]=o.useState(!1),w=o.useRef(null),E=async()=>{const V=`/api/business/review/delete?replyId=${s.reviewReplyId}`;try{const j=(await L.delete(V,{headers:{Authorization:`Bearer ${d}`}})).data;return console.log("삭제",j),y.success("삭제가 완료되었습니다"),j&&(c==null||c("delete"),r(!1),f(!1)),j}catch(g){return console.log("삭제",g),y.error("삭제를 실패했습니다"),null}},T=[{label:e.jsxs("div",{className:"flex items-center gap-3 px-4 py-[14px] text-lg text-slate-500",children:[e.jsx(Q,{className:"text-slate-300"}),"수정하기"]}),onClick:()=>z()},{label:e.jsxs("div",{className:"flex items-center gap-3 px-4 py-[14px] text-lg text-slate-500",children:[e.jsx(Y,{className:"text-slate-400"}),"삭제하기"]}),onClick:()=>F()}],O=[{label:e.jsxs("div",{className:"flex items-center gap-3 px-4 py-[14px] text-lg text-slate-500",children:[e.jsx(A,{}),"신고하기"]}),onClick:()=>u()}],M=()=>{r(!N)},_=()=>{b({...v,userReview:s}),p()},z=()=>{b({...v,userReview:s,reviewReply:s.reviewReply??""}),n()},F=()=>{f(!0),r(!1)};return o.useEffect(()=>{w.current&&S(w.current.scrollHeight>72)},[s.content]),e.jsxs("div",{className:"flex flex-col gap-4 px-4 py-5",children:[e.jsxs("section",{className:"flex flex-col gap-3",children:[e.jsxs("div",{className:"flex items-center justify-between",children:[e.jsxs("div",{className:"flex items-center gap-4",children:[e.jsx("div",{className:"bg-slate-200 rounded-full w-10 h-10 overflow-hidden",children:e.jsx("img",{src:`${G}/${s.userId}/${s.writerUserProfilePic}`,alt:"프로필 사진",className:"w-full h-full object-cover"})}),e.jsx("p",{className:"text-lg font-semibold text-slate-700",children:s.userName})]}),e.jsx("button",{type:"button",className:"text-slate-500 text-2xl font-semibold",onClick:()=>{m(!0)},children:e.jsx(C,{})})]}),e.jsxs("div",{className:"flex flex-col gap-2",children:[e.jsxs("div",{className:"flex items-center gap-2",children:[e.jsx(se,{value:s.ratingAvg,disabled:!0}),e.jsx("p",{className:"text-sm font-semibold text-slate-700",children:s.ratingAvg})]}),e.jsx("div",{className:"flex items-cneter gap-2",children:e.jsx("p",{className:"text-sm text-slate-500",children:s.createdAt})})]}),e.jsxs("div",{children:[e.jsx("p",{ref:w,className:`text-base text-slate-700 overflow-hidden transition-[max-height] duration-500 ease-in-out ${h?"max-h-[1000px]":"max-h-[72px]"}`,children:s.content}),I&&e.jsx("button",{type:"button",className:"text-primary text-base font-semibold mt-2",onClick:()=>R(!h),children:h?"접기":"더보기"})]}),e.jsx(ae,{imgArr:s.reviewPicList,reviewId:s.reviewId})]}),s.reviewReply&&x!=="edit"&&e.jsxs("section",{className:"p-4 bg-slate-100 rounded-lg flex flex-col gap-3",children:[e.jsxs("div",{className:"flex items-center justify-between",children:[e.jsx("p",{className:"text-lg font-semibold text-slate-700",children:"사장님"}),e.jsxs("div",{className:"flex items-center gap-2",children:[e.jsx("p",{className:"text-sm text-slate-500",children:s.reviewReplyCreatedAt}),$!=="/business/review/edit"&&e.jsx("button",{type:"button",className:"text-slate-500 text-xl font-semibold",onClick:()=>{r(!0)},children:e.jsx(C,{})})]})]}),e.jsx("div",{children:e.jsx("p",{className:"text-base text-slate-700",children:s.reviewReply})})]}),$==="/business/review/edit"||s.reviewReply?null:e.jsx(J,{color:"primary",variant:"outlined",className:"text-xl font-semibold px-3 py-1 max-h-[50px] h-[16vw]",onClick:_,children:"댓글 작성"}),e.jsx(k,{open:N,onClose:M,actions:T}),e.jsx(k,{open:B,onClose:()=>m(!1),actions:O}),P&&e.jsx(K,{handleClickCancle:()=>f(!1),handleClickSubmit:E,content:"삭제하시겠습니까?"})]})};export{xe as R,te as e};
