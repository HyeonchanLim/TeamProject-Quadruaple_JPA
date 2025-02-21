import{r,c as D,R as ee,w as ae,u as te,j as e,d as L,g as le,x as ie,L as ce}from"./index-H5Y2xFEd.js";import{T as de,R as xe}from"./TitleHeader-X3y5oUDx.js";import{c as J}from"./index-CzD9Fb6d.js";import{u as pe,a as Q,D as me,c as ue,b as he,v as ge,S as Y,K as fe,P as ye}from"./ScheduleDay-C0itas13.js";import{t as je}from"./tripAtom-DwELmGa1.js";import{a as be}from"./MarkerClusterer-iB_JtRV6.js";import{d as A}from"./dayjs.min-CdNbBogm.js";/* empty css                    */import"./UserTrips-QjL_Bwhs.js";import"./swiper-react-BOiOtV5W.js";/* empty css               */import{c as we,D as ke}from"./index-Ciil3oU-.js";import{n as se,D as Ie}from"./emotion-styled.browser.esm-C84xyw_W.js";import{e as W}from"./EditTripAtom-gRxVrfuh.js";import{C as ne}from"./index-AmSHTzOV.js";import{F as g}from"./index-DFVhX-h-.js";import{I as ve}from"./index-2avQ3JeW.js";import{B as X}from"./button-CWLWs435.js";import"./index-Cnc6HU17.js";import"./match-Cm5iV9Qz.js";import"./index-C-g4QCjD.js";import"./pic-Jp7D6aQ0.js";import"./CenterModal-DQa0aXDd.js";import"./index-DIA0nrpJ.js";import"./useId-D0Wg6OZK.js";import"./index-BQMakcS5.js";import"./index-DCcoDUJV.js";import"./Portal-C5aUVE5q.js";import"./useSize-e4lHQDv7.js";import"./ContextIsolator-CBJzkMqA.js";import"./context-DieX9Lv6.js";import"./Footer-CYw-NSMR.js";import"./collapse-BbEVqHco.js";import"./index-yAcEqhNQ.js";import"./useLocale-DZ4ADMTj.js";import"./index-CeBGZAtP.js";import"./BaseInput-wj3I2EyG.js";import"./PurePanel-CsS_zZWe.js";import"./Overflow-eqLeprlC.js";import"./useIcons-YOlftlEt.js";import"./CheckOutlined-D9k58vf_.js";import"./EllipsisOutlined-BjYo5MPY.js";import"./index-DSd5097F.js";import"./Checkbox-DtB9rWkt.js";const{RangePicker:Z}=ke;A.extend(we);const Ce=se(ne)`
  && {
    .ant-checkbox {
      order: 2;
      transform: scale(2);
      margin-left: 8px;
      margin-right: 0;

      // 체크박스를 동그라미로 만들기
      .ant-checkbox-inner {
        border-radius: 50%; // 동그라미 모양으로 변경
        &::after {
          inset-inline-start: 25%; // 체크 마크의 시작 위치를 조정
          top: 45%; // 상단 위치 미세 조정
          width: 5.714286px; // 체크 마크 크기 조정
          height: 9.142857px; // 체크 마크 크기 조정
        }
      }
    }
    .ant-checkbox-wrapper {
      display: flex;
      flex-direction: row;
      justify-content: space-between;
      align-items: center;
      width: 100%;
    }
    .ant-checkbox + span {
      order: 1;
      padding-left: 0;
      padding-right: 8px;
    }
  }
`,Ne=({tripData:t,handleClickCancle:f,getTrip:T})=>{var b,c;const[l]=g.useForm(),[y]=D(),j=parseInt(y.get("tripId")),[V,E]=ee(W),n=ae(W),p=te(),_=()=>{f()},P=s=>{s.stopPropagation()},C=(s,a)=>{console.log("선택된 날짜:",s),console.log("포맷된 날짜:",a);const m=a==null?void 0:a.map(N=>N+":00");setDates(m)},U=s=>s&&s<A().endOf("day");se(Z)`
    .ant-picker-input input {
      color: "#334155" !important;
      display: flex;
      justify-content: center;
      align-items: center;
      padding: 0 10px;
      width: 130px;
    }
  `;const h=async s=>{try{const a=await L.patch("/api/trip",s);console.log("여행 수정",a.data);const m=a.data;m&&(T(),f()),m.code==="200 성공"&&n()}catch(a){console.log("여행 수정",a)}},$=s=>{var S;console.log(s);const{title:a,rangePicker:m,nowUser:N}=s,w=t!=null&&t.nowUser?t.nowUser.map(x=>x.userId).filter(x=>{var u;return!((u=s.nowUser)!=null&&u.includes(x))}):[];(S=s.nowUser)!=null&&S.filter(x=>!w.includes(x));const k=m.map(x=>x.format("YYYY-MM-DD"));console.log(k);const i={title:a,trip_id:j,start_at:k[0],end_at:k[1],del_user_list:w,ins_location_list:[...t==null?void 0:t.tripLocationList],del_location_list:[]};h(i)},F=()=>{console.log("지역 선택"),E({title:t.title,startDate:t.startAt,endDate:t.endAt,nowUser:t.tripUserIdList,tripLocationList:t.tripLocationList,from:`/schedule/index?tripId=${j}`,isEdit:!0}),p("/search/location",{state:{tripLocationList:t.tripLocationList,from:`/schedule/index?tripId=${j}`}})};return e.jsx("div",{className:`fixed top-0 left-[50%] translate-x-[-50%] z-10\r
            max-w-3xl w-full mx-auto h-screen\r
            flex items-center justify-center\r
            bg-[rgba(0,0,0,0.5)]\r
            `,onClick:()=>{_()},children:e.jsxs("div",{className:`bg-white \r
                rounded-2xl px-[60px] pt-[30px] pb-[10px]\r
                flex flex-col items-center justify-center\r
                gap-[20px]\r
                `,onClick:P,children:[e.jsxs("div",{className:"flex gap-[20px]",children:[e.jsx("button",{type:"button",onClick:F,children:"지역 선택"}),e.jsx("ul",{children:(b=t==null?void 0:t.tripLocationList)==null?void 0:b.map((s,a)=>e.jsx("li",{children:s},a))})]}),e.jsxs(g,{form:l,onFinish:$,requiredMark:!1,style:{width:"100%"},children:[e.jsx(g.Item,{name:"title",label:"제목",rules:[{required:!0,message:"제목을 입력해주세요."}],initialValue:t.title,children:e.jsx(ve,{})}),e.jsx(g.Item,{name:"rangePicker",label:"여행일자",rules:[{required:!0,message:"여행일자를 입력해주세요."}],initialValue:t!=null&&t.startAt&&(t!=null&&t.endAt)?[A(t.startAt),A(t.endAt)]:void 0,children:e.jsx(Z,{placeholder:["시작일 ","종료일"],disabledDate:U,variant:"borderless",onChange:C,separator:"~"})}),e.jsx(g.Item,{name:"nowUser",label:"참여자",initialValue:(t==null?void 0:t.tripUserIdList)||[],children:e.jsx(ne.Group,{children:((c=t==null?void 0:t.tripUserIdList)==null?void 0:c.map((s,a)=>e.jsx(Ce,{value:s,size:"large",children:s},a)))||null})}),e.jsx(g.Item,{style:{width:"100%"},children:e.jsxs("div",{style:{display:"flex",gap:"10px"},children:[e.jsx(X,{color:"default",variant:"filled",htmlType:"button",onClick:f,style:{width:"50%"},children:"취소"}),e.jsx(X,{type:"primary",htmlType:"submit",style:{width:"50%"},children:"수정"})]})})]})]})})},Se=r.memo(Ne),Le={day:1,weather:"",schedules:[]},Ae=()=>{var z;le("accessToken");const[t,f]=ee(je);r.useEffect(()=>{console.log("trip",t)},[t]);const[T]=D(),l=parseInt(T.get("tripId"));r.useEffect(()=>{f({...t,nowTripId:l})},[]);const y=te(),j=()=>{y(-1)},V=()=>{y(`/schedule/calculation?tripId=${l}`)},E=()=>{y(`/scheduleboard/schedulePost?tripId=${l}`)},[n,p]=r.useState({}),[_,P]=r.useState(!1),[C,U]=r.useState(n.title),[h,$]=r.useState(""),[F,b]=r.useState(!1),[c,s]=r.useState(!1);r.useEffect(()=>{i()},[]),r.useState(!1);const[a,m]=r.useState({tripId:null,scheduleId:null,originDay:null,destDay:null,originSeq:null,destSeq:null});r.useEffect(()=>{console.log("여행 데이터",n)},[n]),r.useEffect(()=>{console.log("링크",h)},[h]),r.useEffect(()=>{console.log("title",C)},[C]);const N=r.useCallback(async()=>{try{const o=await L.get(`/api/trip/add-link?trip_id=${l}`);console.log(o.data),$(o.data.data)}catch(o){console.log("초대코드",o)}},[]),w=r.useCallback(async()=>{try{await navigator.clipboard.writeText(h),console.log("복사 성공")}catch(o){console.error("복사 실패:",o)}},[]),k=[{label:e.jsxs("div",{onClick:()=>w(),className:"flex flex-col gap-[10px] items-center justify-center",children:[e.jsx("p",{className:"bg-slate-100 px-[15px] py-[10px] rounded-lg text-slate-600",children:h}),e.jsxs("p",{className:"flex items-center gap-1 border-b border-slate-300",children:[e.jsx("i",{className:"text-slate-500",children:e.jsx(be,{})}),e.jsx("span",{className:"text-slate-500",children:"초대코드 복사하기"})]})]}),key:"0"}],i=r.useCallback(async()=>{try{const o=await L.get(`/api/trip?trip_id=${l}&signed=true`);console.log("여행확인하기",o.data);const d=o.data.data;p(d),d&&P(!0)}catch(o){console.log(o)}},[]),S=r.useCallback(async o=>{try{const d=await L.patch("/api/schedule",o);console.log("일정 순서 변경 성공:",d.data)}catch(d){console.log("일정 순서 변경 실패:",d),i()}},[]),x=()=>{b(!1)};r.useEffect(()=>{i(),n&&U(n.title)},[]);const u=n.days,oe=pe(Q(ye),Q(fe)),re=async o=>{const{active:d,over:M}=o;if(!M)return;const[I,q]=d.id.split("-").map(Number),[R,B]=M.id.split("-").map(Number);if(I===R&&q===B)return;const O=structuredClone(n),K=O.days.find(v=>v.day===I),G=O.days.find(v=>v.day===R);if(!K||!G)return;const[H]=K.schedules.splice(q,1);G.schedules.splice(B,0,H),p(O);try{await S({tripId:l,scheduleId:H.scheduleMemoId,originDay:I,destDay:R,originSeq:q+1,destSeq:B+1})}catch(v){console.error("Failed to update schedule order:",v),i()}};return r.useEffect(()=>{a.tripId&&console.log("드래그 정보 상태 업데이트:",a)},[a]),e.jsxs("div",{children:[_?e.jsxs(e.Fragment,{children:[e.jsx(de,{icon:"back",onClick:j,rightContent:e.jsx(xe,{icon1:!1,icon2:!0,icon3Click:E,icon3:!0,icon4:!0})}),e.jsxs("div",{className:"flex flex-col gap-[30px] py-[30px]",children:[e.jsxs("div",{className:"mt-[60px] flex flex-col gap-[10px] px-[32px]",children:[e.jsxs("div",{className:"flex items-center justify-between",children:[e.jsxs("p",{className:"text-[18px] text-slate-700 ",children:[e.jsx("span",{children:n.startAt}),"-",e.jsx("span",{children:n.endAt})]}),e.jsx("button",{type:"button",onClick:()=>b(!0),children:e.jsx(ie,{className:"text-[24px] text-slate-300 bg-white"})})]}),e.jsx("h2",{className:"text-[36px] text-slate-700 font-bold",children:n.title}),e.jsx("div",{className:"flex items-center gap-3 mt-5"})]}),e.jsxs("div",{className:"flex items-center justify-between gap-[10px] px-[32px]",children:[e.jsxs("div",{className:"flex items-center gap-[10px]",children:[e.jsx(Ie,{menu:{items:k},trigger:["click"],overlayStyle:{marginTop:"10px"},children:e.jsxs("button",{type:"button",className:`flex items-center gap-[10px] \r
                  px-[15px] py-[10px] rounded-3xl\r
                  text-white bg-primary\r
                  hover:bg-primary/80 transition-all duration-300`,onClick:async()=>{await N(),w()},children:[e.jsx(J,{}),"초대 코드"]})}),e.jsxs("button",{type:"button",className:`flex items-center gap-[10px] \r
                px-[15px] py-[10px] rounded-3xl\r
                text-slate-500 bg-slate-100\r
                hover:bg-slate-200/80 transition-all duration-300`,onClick:V,children:[e.jsx(J,{className:"text-slate-300"}),"가계부"]})]}),e.jsxs("div",{className:"flex items-center gap-[10px]",children:[e.jsx("button",{type:"button",className:`flex items-center gap-[10px] \r
                  px-[15px] py-[10px] rounded-3xl\r
                  text-slate-500 bg-slate-100\r
                  hover:bg-slate-200/80 transition-all duration-300`,children:"참여 인원"}),e.jsx("button",{type:"button",className:`flex items-center gap-[10px] 
                    px-[15px] py-[10px] rounded-3xl transition-all duration-300
                    ${c?"text-white bg-primary hover:bg-primary/80":"text-slate-500 bg-slate-100 hover:bg-slate-200/80"}`,onClick:()=>{c?(s(!1),i()):s(!0)},children:c?"완료":"일정 편집"})]})]}),e.jsx("div",{className:"flex flex-col gap-[50px]",children:c?e.jsx(me,{sensors:oe,collisionDetection:ue,onDragEnd:re,children:(z=n.days)==null?void 0:z.map((o,d)=>e.jsx(he,{items:o.schedules.map((M,I)=>`${o.day}-${I}`),strategy:ge,children:e.jsx(Y,{newTrip:!0,data:o,startAt:n==null?void 0:n.startAt,tripId:l,getTrip:i,setTripData:p,isDragging:c,setIsDragging:s})},o.day))}):e.jsx(e.Fragment,{children:u===null?e.jsx(Y,{newTrip:!0,data:Le,startAt:n==null?void 0:n.startAt,tripId:l,getTrip:i,setTripData:p,isDragging:c,setIsDragging:s}):u==null?void 0:u.map((o,d)=>e.jsx(Y,{newTrip:!0,data:o,startAt:n==null?void 0:n.startAt,tripId:l,getTrip:i,setTripData:p,isDragging:c,setIsDragging:s},o.day))})})]})]}):e.jsx(ce,{}),F&&e.jsx(Se,{tripData:n,handleClickCancle:x,getTrip:i})]})},bt=r.memo(Ae);export{bt as default};
