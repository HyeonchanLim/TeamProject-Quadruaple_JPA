import{r as a,at as He,bE as Ke,av as le,ar as P,aA as fe,bJ as We,_ as he,bG as pe,o as T,au as ve,bo as me,aP as be,bm as $e,v as Ue,w as Ve,q as Q,bI as Xe,bN as Ge,bL as qe,bO as Je,D as Ze,j as t,u as De,g as Oe,m as xe,d as ee,a0 as Qe,ae as et,B as oe,bU as tt,X as re,F as at,h as nt,J as ye,i as st,M as ot,T as rt}from"./index-CclgMCKg.js";import{j as lt}from"./jwt-Be9xYbvd.js";import{F as it}from"./Footer--G79VcmM.js";import{B as ct}from"./BottomSheet-nkPRIX3f.js";import{b as dt}from"./index-DLFP4kOL.js";import"./ko-Xz6RHycB.js";import{c as ut}from"./customParseFormat--lJaNAof.js";import{P as mt}from"./Portal-cEDniDzA.js";import{g as je}from"./motion-BtiDeT7c.js";import{u as ft,p as Ce,a as ht}from"./context-BaOhzo3B.js";import{S as xt}from"./Skeleton-B7Q4exgZ.js";/* empty css                    */import{T as gt}from"./index-Bp8J61U8.js";import"./proxy-luPhEEJa.js";import"./EllipsisOutlined-BwAZKzUe.js";import"./index-Dx4kxOlF.js";import"./useId-C98JHZMn.js";import"./isMobile-DjGTsQxe.js";import"./Overflow-b40CqTdl.js";var we=a.createContext(null),Ee=a.createContext({}),pt=["prefixCls","className","containerRef"],vt=function(n){var o=n.prefixCls,s=n.className,r=n.containerRef,u=He(n,pt),l=a.useContext(Ee),i=l.panel,p=Ke(i,r);return a.createElement("div",le({className:P("".concat(o,"-content"),s),role:"dialog",ref:p},fe(n,{aria:!0}),{"aria-modal":"true"},u))};function ke(e){return typeof e=="string"&&String(Number(e))===e?(We(!1,"Invalid value type of `width` or `height` which should be number type instead."),Number(e)):e}var Se={width:0,height:0,overflow:"hidden",outline:"none",position:"absolute"};function bt(e,n){var o,s,r,u=e.prefixCls,l=e.open,i=e.placement,p=e.inline,w=e.push,N=e.forceRender,v=e.autoFocus,k=e.keyboard,c=e.classNames,m=e.rootClassName,h=e.rootStyle,b=e.zIndex,d=e.className,x=e.id,$=e.style,O=e.motion,j=e.width,S=e.height,Y=e.children,z=e.mask,B=e.maskClosable,E=e.maskMotion,_=e.maskClassName,W=e.maskStyle,F=e.afterOpenChange,f=e.onClose,D=e.onMouseEnter,M=e.onMouseOver,L=e.onMouseLeave,R=e.onClick,te=e.onKeyDown,ae=e.onKeyUp,C=e.styles,U=e.drawerRender,A=a.useRef(),H=a.useRef(),K=a.useRef();a.useImperativeHandle(n,function(){return A.current});var ne=function(I){var q=I.keyCode,J=I.shiftKey;switch(q){case me.TAB:{if(q===me.TAB){if(!J&&document.activeElement===K.current){var Z;(Z=H.current)===null||Z===void 0||Z.focus({preventScroll:!0})}else if(J&&document.activeElement===H.current){var ue;(ue=K.current)===null||ue===void 0||ue.focus({preventScroll:!0})}}break}case me.ESC:{f&&k&&(I.stopPropagation(),f(I));break}}};a.useEffect(function(){if(l&&v){var g;(g=A.current)===null||g===void 0||g.focus({preventScroll:!0})}},[l]);var ie=a.useState(!1),ce=he(ie,2),se=ce[0],G=ce[1],y=a.useContext(we),de;typeof w=="boolean"?de=w?{}:{distance:0}:de=w||{};var V=(o=(s=(r=de)===null||r===void 0?void 0:r.distance)!==null&&s!==void 0?s:y==null?void 0:y.pushDistance)!==null&&o!==void 0?o:180,ze=a.useMemo(function(){return{pushDistance:V,push:function(){G(!0)},pull:function(){G(!1)}}},[V]);a.useEffect(function(){if(l){var g;y==null||(g=y.push)===null||g===void 0||g.call(y)}else{var I;y==null||(I=y.pull)===null||I===void 0||I.call(y)}},[l]),a.useEffect(function(){return function(){var g;y==null||(g=y.pull)===null||g===void 0||g.call(y)}},[]);var Le=z&&a.createElement(pe,le({key:"mask"},E,{visible:l}),function(g,I){var q=g.className,J=g.style;return a.createElement("div",{className:P("".concat(u,"-mask"),q,c==null?void 0:c.mask,_),style:T(T(T({},J),W),C==null?void 0:C.mask),onClick:B&&l?f:void 0,ref:I})}),Te=typeof O=="function"?O(i):O,X={};if(se&&V)switch(i){case"top":X.transform="translateY(".concat(V,"px)");break;case"bottom":X.transform="translateY(".concat(-V,"px)");break;case"left":X.transform="translateX(".concat(V,"px)");break;default:X.transform="translateX(".concat(-V,"px)");break}i==="left"||i==="right"?X.width=ke(j):X.height=ke(S);var Ae={onMouseEnter:D,onMouseOver:M,onMouseLeave:L,onClick:R,onKeyDown:te,onKeyUp:ae},Fe=a.createElement(pe,le({key:"panel"},Te,{visible:l,forceRender:N,onVisibleChanged:function(I){F==null||F(I)},removeOnLeave:!1,leavedClassName:"".concat(u,"-content-wrapper-hidden")}),function(g,I){var q=g.className,J=g.style,Z=a.createElement(vt,le({id:x,containerRef:I,prefixCls:u,className:P(d,c==null?void 0:c.content),style:T(T({},$),C==null?void 0:C.content)},fe(e,{aria:!0}),Ae),Y);return a.createElement("div",le({className:P("".concat(u,"-content-wrapper"),c==null?void 0:c.wrapper,q),style:T(T(T({},X),J),C==null?void 0:C.wrapper)},fe(e,{data:!0})),U?U(Z):Z)}),ge=T({},h);return b&&(ge.zIndex=b),a.createElement(we.Provider,{value:ze},a.createElement("div",{className:P(u,"".concat(u,"-").concat(i),m,ve(ve({},"".concat(u,"-open"),l),"".concat(u,"-inline"),p)),style:ge,tabIndex:-1,ref:A,onKeyDown:ne},Le,a.createElement("div",{tabIndex:0,ref:H,style:Se,"aria-hidden":"true","data-sentinel":"start"}),Fe,a.createElement("div",{tabIndex:0,ref:K,style:Se,"aria-hidden":"true","data-sentinel":"end"})))}var yt=a.forwardRef(bt),jt=function(n){var o=n.open,s=o===void 0?!1:o,r=n.prefixCls,u=r===void 0?"rc-drawer":r,l=n.placement,i=l===void 0?"right":l,p=n.autoFocus,w=p===void 0?!0:p,N=n.keyboard,v=N===void 0?!0:N,k=n.width,c=k===void 0?378:k,m=n.mask,h=m===void 0?!0:m,b=n.maskClosable,d=b===void 0?!0:b,x=n.getContainer,$=n.forceRender,O=n.afterOpenChange,j=n.destroyOnClose,S=n.onMouseEnter,Y=n.onMouseOver,z=n.onMouseLeave,B=n.onClick,E=n.onKeyDown,_=n.onKeyUp,W=n.panelRef,F=a.useState(!1),f=he(F,2),D=f[0],M=f[1],L=a.useState(!1),R=he(L,2),te=R[0],ae=R[1];be(function(){ae(!0)},[]);var C=te?s:!1,U=a.useRef(),A=a.useRef();be(function(){C&&(A.current=document.activeElement)},[C]);var H=function(se){var G;if(M(se),O==null||O(se),!se&&A.current&&!((G=U.current)!==null&&G!==void 0&&G.contains(A.current))){var y;(y=A.current)===null||y===void 0||y.focus({preventScroll:!0})}},K=a.useMemo(function(){return{panel:W}},[W]);if(!$&&!D&&!C&&j)return null;var ne={onMouseEnter:S,onMouseOver:Y,onMouseLeave:z,onClick:B,onKeyDown:E,onKeyUp:_},ie=T(T({},n),{},{open:C,prefixCls:u,placement:i,autoFocus:w,keyboard:v,width:c,mask:h,maskClosable:d,inline:x===!1,afterOpenChange:H,ref:U},ne);return a.createElement(Ee.Provider,{value:K},a.createElement(mt,{open:C||$||D,autoDestroy:!1,getContainer:x,autoLock:h&&(C||D)},a.createElement(yt,ie)))};const Ie=e=>{var n,o;const{prefixCls:s,title:r,footer:u,extra:l,loading:i,onClose:p,headerStyle:w,bodyStyle:N,footerStyle:v,children:k,classNames:c,styles:m}=e,h=$e("drawer"),b=a.useCallback(j=>a.createElement("button",{type:"button",onClick:p,"aria-label":"Close",className:`${s}-close`},j),[p]),[d,x]=ft(Ce(e),Ce(h),{closable:!0,closeIconRender:b}),$=a.useMemo(()=>{var j,S;return!r&&!d?null:a.createElement("div",{style:Object.assign(Object.assign(Object.assign({},(j=h.styles)===null||j===void 0?void 0:j.header),w),m==null?void 0:m.header),className:P(`${s}-header`,{[`${s}-header-close-only`]:d&&!r&&!l},(S=h.classNames)===null||S===void 0?void 0:S.header,c==null?void 0:c.header)},a.createElement("div",{className:`${s}-header-title`},x,r&&a.createElement("div",{className:`${s}-title`},r)),l&&a.createElement("div",{className:`${s}-extra`},l))},[d,x,l,w,s,r]),O=a.useMemo(()=>{var j,S;if(!u)return null;const Y=`${s}-footer`;return a.createElement("div",{className:P(Y,(j=h.classNames)===null||j===void 0?void 0:j.footer,c==null?void 0:c.footer),style:Object.assign(Object.assign(Object.assign({},(S=h.styles)===null||S===void 0?void 0:S.footer),v),m==null?void 0:m.footer)},u)},[u,v,s]);return a.createElement(a.Fragment,null,$,a.createElement("div",{className:P(`${s}-body`,c==null?void 0:c.body,(n=h.classNames)===null||n===void 0?void 0:n.body),style:Object.assign(Object.assign(Object.assign({},(o=h.styles)===null||o===void 0?void 0:o.body),N),m==null?void 0:m.body)},i?a.createElement(xt,{active:!0,title:!1,paragraph:{rows:5},className:`${s}-body-skeleton`}):k),O)},Ct=e=>{const n="100%";return{left:`translateX(-${n})`,right:`translateX(${n})`,top:`translateY(-${n})`,bottom:`translateY(${n})`}[e]},Pe=(e,n)=>({"&-enter, &-appear":Object.assign(Object.assign({},e),{"&-active":n}),"&-leave":Object.assign(Object.assign({},n),{"&-active":e})}),Me=(e,n)=>Object.assign({"&-enter, &-appear, &-leave":{"&-start":{transition:"none"},"&-active":{transition:`all ${n}`}}},Pe({opacity:e},{opacity:1})),wt=(e,n)=>[Me(.7,n),Pe({transform:Ct(e)},{transform:"none"})],kt=e=>{const{componentCls:n,motionDurationSlow:o}=e;return{[n]:{[`${n}-mask-motion`]:Me(0,o),[`${n}-panel-motion`]:["left","right","top","bottom"].reduce((s,r)=>Object.assign(Object.assign({},s),{[`&-${r}`]:wt(r,o)}),{})}}},St=e=>{const{borderRadiusSM:n,componentCls:o,zIndexPopup:s,colorBgMask:r,colorBgElevated:u,motionDurationSlow:l,motionDurationMid:i,paddingXS:p,padding:w,paddingLG:N,fontSizeLG:v,lineHeightLG:k,lineWidth:c,lineType:m,colorSplit:h,marginXS:b,colorIcon:d,colorIconHover:x,colorBgTextHover:$,colorBgTextActive:O,colorText:j,fontWeightStrong:S,footerPaddingBlock:Y,footerPaddingInline:z,calc:B}=e,E=`${o}-content-wrapper`;return{[o]:{position:"fixed",inset:0,zIndex:s,pointerEvents:"none",color:j,"&-pure":{position:"relative",background:u,display:"flex",flexDirection:"column",[`&${o}-left`]:{boxShadow:e.boxShadowDrawerLeft},[`&${o}-right`]:{boxShadow:e.boxShadowDrawerRight},[`&${o}-top`]:{boxShadow:e.boxShadowDrawerUp},[`&${o}-bottom`]:{boxShadow:e.boxShadowDrawerDown}},"&-inline":{position:"absolute"},[`${o}-mask`]:{position:"absolute",inset:0,zIndex:s,background:r,pointerEvents:"auto"},[E]:{position:"absolute",zIndex:s,maxWidth:"100vw",transition:`all ${l}`,"&-hidden":{display:"none"}},[`&-left > ${E}`]:{top:0,bottom:0,left:{_skip_check_:!0,value:0},boxShadow:e.boxShadowDrawerLeft},[`&-right > ${E}`]:{top:0,right:{_skip_check_:!0,value:0},bottom:0,boxShadow:e.boxShadowDrawerRight},[`&-top > ${E}`]:{top:0,insetInline:0,boxShadow:e.boxShadowDrawerUp},[`&-bottom > ${E}`]:{bottom:0,insetInline:0,boxShadow:e.boxShadowDrawerDown},[`${o}-content`]:{display:"flex",flexDirection:"column",width:"100%",height:"100%",overflow:"auto",background:u,pointerEvents:"auto"},[`${o}-header`]:{display:"flex",flex:0,alignItems:"center",padding:`${Q(w)} ${Q(N)}`,fontSize:v,lineHeight:k,borderBottom:`${Q(c)} ${m} ${h}`,"&-title":{display:"flex",flex:1,alignItems:"center",minWidth:0,minHeight:0}},[`${o}-extra`]:{flex:"none"},[`${o}-close`]:Object.assign({display:"inline-flex",width:B(v).add(p).equal(),height:B(v).add(p).equal(),borderRadius:n,justifyContent:"center",alignItems:"center",marginInlineEnd:b,color:d,fontWeight:S,fontSize:v,fontStyle:"normal",lineHeight:1,textAlign:"center",textTransform:"none",textDecoration:"none",background:"transparent",border:0,cursor:"pointer",transition:`all ${i}`,textRendering:"auto","&:hover":{color:x,backgroundColor:$,textDecoration:"none"},"&:active":{backgroundColor:O}},Xe(e)),[`${o}-title`]:{flex:1,margin:0,fontWeight:e.fontWeightStrong,fontSize:v,lineHeight:k},[`${o}-body`]:{flex:1,minWidth:0,minHeight:0,padding:N,overflow:"auto",[`${o}-body-skeleton`]:{width:"100%",height:"100%",display:"flex",justifyContent:"center"}},[`${o}-footer`]:{flexShrink:0,padding:`${Q(Y)} ${Q(z)}`,borderTop:`${Q(c)} ${m} ${h}`},"&-rtl":{direction:"rtl"}}}},Nt=e=>({zIndexPopup:e.zIndexPopupBase,footerPaddingBlock:e.paddingXS,footerPaddingInline:e.padding}),Re=Ue("Drawer",e=>{const n=Ve(e,{});return[St(n),kt(n)]},Nt);var Be=function(e,n){var o={};for(var s in e)Object.prototype.hasOwnProperty.call(e,s)&&n.indexOf(s)<0&&(o[s]=e[s]);if(e!=null&&typeof Object.getOwnPropertySymbols=="function")for(var r=0,s=Object.getOwnPropertySymbols(e);r<s.length;r++)n.indexOf(s[r])<0&&Object.prototype.propertyIsEnumerable.call(e,s[r])&&(o[s[r]]=e[s[r]]);return o};const $t={distance:180},_e=e=>{const{rootClassName:n,width:o,height:s,size:r="default",mask:u=!0,push:l=$t,open:i,afterOpenChange:p,onClose:w,prefixCls:N,getContainer:v,style:k,className:c,visible:m,afterVisibleChange:h,maskStyle:b,drawerStyle:d,contentWrapperStyle:x}=e,$=Be(e,["rootClassName","width","height","size","mask","push","open","afterOpenChange","onClose","prefixCls","getContainer","style","className","visible","afterVisibleChange","maskStyle","drawerStyle","contentWrapperStyle"]),{getPopupContainer:O,getPrefixCls:j,direction:S,className:Y,style:z,classNames:B,styles:E}=$e("drawer"),_=j("drawer",N),[W,F,f]=Re(_),D=v===void 0&&O?()=>O(document.body):v,M=P({"no-mask":!u,[`${_}-rtl`]:S==="rtl"},n,F,f),L=a.useMemo(()=>o??(r==="large"?736:378),[o,r]),R=a.useMemo(()=>s??(r==="large"?736:378),[s,r]),te={motionName:je(_,"mask-motion"),motionAppear:!0,motionEnter:!0,motionLeave:!0,motionDeadline:500},ae=ne=>({motionName:je(_,`panel-motion-${ne}`),motionAppear:!0,motionEnter:!0,motionLeave:!0,motionDeadline:500}),C=ht(),[U,A]=Ge("Drawer",$.zIndex),{classNames:H={},styles:K={}}=$;return W(a.createElement(qe,{form:!0,space:!0},a.createElement(Je.Provider,{value:A},a.createElement(jt,Object.assign({prefixCls:_,onClose:w,maskMotion:te,motion:ae},$,{classNames:{mask:P(H.mask,B.mask),content:P(H.content,B.content),wrapper:P(H.wrapper,B.wrapper)},styles:{mask:Object.assign(Object.assign(Object.assign({},K.mask),b),E.mask),content:Object.assign(Object.assign(Object.assign({},K.content),d),E.content),wrapper:Object.assign(Object.assign(Object.assign({},K.wrapper),x),E.wrapper)},open:i??m,mask:u,push:l,width:L,height:R,style:Object.assign(Object.assign({},z),k),className:P(Y,c),rootClassName:M,getContainer:D,afterOpenChange:p??h,panelRef:C,zIndex:U}),a.createElement(Ie,Object.assign({prefixCls:_},$,{onClose:w}))))))},Dt=e=>{const{prefixCls:n,style:o,className:s,placement:r="right"}=e,u=Be(e,["prefixCls","style","className","placement"]),{getPrefixCls:l}=a.useContext(Ze),i=l("drawer",n),[p,w,N]=Re(i),v=P(i,`${i}-pure`,`${i}-${r}`,w,N,s);return p(a.createElement("div",{className:v,style:o},a.createElement(Ie,Object.assign({prefixCls:i},u))))};_e._InternalPanelDoNotUseOrYouWillBeFired=Dt;const Ot=()=>t.jsx("div",{children:t.jsxs("div",{children:[t.jsx("h2",{children:"숙소 환불 정책"}),t.jsx("p",{children:"때로 불가피한 상황으로 인해 게스트가 예약을 취소해야 하는 경우가 생깁니다."}),t.jsx("p",{children:"원활한 호스팅을 위해, 단기 숙박에 적용되는 환불 정책과 장기 숙박에 적용되는 환불 정책을 각각 선택하실 수 있습니다."}),t.jsx("p",{children:"숙박 기간에 따라 별도의 환불 정책을 설정하고 싶다면, 숙소 환불 정책을 설정하는 방법을 확인해 보세요."}),t.jsx("p",{children:"환불 정책을 선택할 때는 해당 정책이 현지 법규에 부합하는지 확인하셔야 합니다."}),t.jsx("p",{children:"전액 환불이라고 할 때, '전액'은 숙박비만을 지칭합니다."}),t.jsx("p",{children:"쿼트러플 게스트 수수료 환불 여부는 여러 요인에 따라 달라질 수 있습니다."}),t.jsx("p",{children:"게스트가 체크인 전에 취소하면 호스트에게 청소비가 지급되지 않습니다."}),t.jsx("h3",{children:"단기 숙박에 적용되는 표준 환불 정책"}),t.jsx("p",{children:"표준 환불 정책은 연속 27박 이하의 모든 예약에 적용되며, 다음 중 하나를 선택하실 수 있습니다."}),t.jsx("h4",{children:"유연"}),t.jsxs("ul",{children:[t.jsx("li",{children:"게스트는 체크인 24시간 전까지 예약을 취소할 경우 전액 환불받을 수 있으며, 호스트에게는 대금이 지급되지 않습니다."}),t.jsx("li",{children:"그 후에 취소하는 경우, 이미 숙박한 일수와 하루치의 숙박비 전액이 호스트에게 지급됩니다."}),t.jsx("li",{children:"일반 게스트가 체크인 5일 전까지 예약을 취소한다면 전액 환불받을 수 있으며, 호스트에게는 대금이 지급되지 않습니다."}),t.jsx("li",{children:"그 후에 취소하는 경우, 이미 숙박한 일수와 하루치의 숙박비 전액 및 남은 숙박 일수에 대한 숙박비 50%가 호스트에게 지급됩니다."})]}),t.jsx("h4",{children:"비교적 엄격"}),t.jsxs("ul",{children:[t.jsx("li",{children:"게스트는 체크인까지 30일 이상 남은 시점에 예약을 취소해야 전액 환불을 받을 수 있습니다."}),t.jsx("li",{children:"체크인까지 7~30일이 남은 시점에 예약을 취소하면, 숙박비의 50%가 호스트에게 지급됩니다."}),t.jsx("li",{children:"체크인까지 7일이 채 남지 않은 시점에 예약을 취소하면, 숙박비 전액이 호스트에게 지급됩니다."}),t.jsx("li",{children:"또한 게스트가 예약 후 48시간 이내에 취소하는 경우, 체크인까지 14일 이상 남았다면 전액 환불받을 수 있습니다."})]}),t.jsx("h4",{children:"엄격"}),t.jsxs("ul",{children:[t.jsx("li",{children:"게스트는 예약 후 48시간 이내에 취소하고 체크인까지 14일 이상이 남은 경우에만 전액 환불을 받을 수 있습니다."}),t.jsx("li",{children:"예약 후 48시간이 지나고 체크인까지 14일 이상이 남은 시점에 예약을 취소하면, 숙박비의 50%가 호스트에게 지급됩니다."}),t.jsx("li",{children:"체크인까지 7~14일이 남은 시점에 예약을 취소하면, 숙박비의 50%가 호스트에게 지급됩니다."}),t.jsx("li",{children:"그 후에 취소하는 경우, 숙박 대금 전액이 호스트에게 지급됩니다."})]}),t.jsx("h4",{children:"매우 엄격 30일"}),t.jsx("p",{children:"게스트가 체크인까지 30일 이상 남은 시점에 취소하면, 숙박 대금의 50%가 환불됩니다."}),t.jsx("p",{children:"그 후에 취소하는 경우, 숙박 대금 전액이 호스트에게 지급됩니다."}),t.jsx("h4",{children:"매우 엄격 60일"}),t.jsx("p",{children:"게스트가 체크인까지 60일 이상 남은 시점에 취소하면, 숙박 대금의 50%가 환불됩니다."}),t.jsx("p",{children:"그 후에 취소하는 경우, 숙박 대금 전액이 호스트에게 지급됩니다."}),t.jsx("h3",{children:"장기 숙박에 적용되는 환불 정책"}),t.jsx("p",{children:"장기 숙박 환불 정책은 연속 18박 이상의 장기 숙박에 적용되며, 다음 중 하나를 선택하실 수 있습니다."}),t.jsx("h4",{children:"비교적 엄격"}),t.jsxs("ul",{children:[t.jsx("li",{children:"게스트는 체크인까지 30일 이상 남은 시점에 예약을 취소해야 전액 환불을 받을 수 있습니다."}),t.jsx("li",{children:"게스트가 그 후에 예약을 취소하는 경우, 이미 숙박한 날짜의 숙박비 전액과 추가 30일에 대한 숙박비가 호스트에게 지급됩니다."}),t.jsx("li",{children:"게스트가 예약을 취소하는 시점에 남은 숙박 일수가 30일 미만이라면, 남은 숙박일 전체에 대한 숙박비가 호스트에게 지급됩니다."})]}),t.jsx("h4",{children:"엄격"}),t.jsxs("ul",{children:[t.jsx("li",{children:"게스트는 예약 후 48시간 이내에 취소하고 체크인까지 28일 이상이 남은 경우에만 전액 환불을 받을 수 있습니다."}),t.jsx("li",{children:"게스트가 그 후에 예약을 취소하는 경우, 이미 숙박한 날짜의 숙박비 전액과 예약된 숙박 기간 중 향후 30일에 대한 숙박비가 호스트에게 지급됩니다."}),t.jsx("li",{children:"게스트가 예약을 취소하는 시점에 남은 숙박 일수가 30일 미만이라면, 남은 숙박일 전체에 대한 숙박비가 호스트에게 지급됩니다."})]}),t.jsx("h4",{children:"단기 숙박에 할인을 적용하고 환불 불가 옵션 제공하기"}),t.jsx("p",{children:"28박 미만의 숙박에 대해 표준 환불 정책을 설정하는 경우, 부가적으로 환불 불가 옵션을 제시할 수 있습니다. 환불 불가 옵션을 선택한 게스트는 할인된 가격으로 숙소를 예약할 수 있으며, 이 경우 표준 환불 정책이 적용되지 않기 때문에 게스트가 취소하는 경우 환불되지 않습니다."})]})});ee.extend(ut);ee.locale("ko");const Et=e=>{console.log(e.bookingId);const{checkInDate:n,checkOutDate:o,strfTitle:s,strfPic:r,price:u,state:l,strfId:i,checkInTime:p,checkOutTime:w,createdAt:N,bookingId:v}=e.data,k=De(),c=Oe("accessToken"),m=[{label:t.jsxs("p",{className:"flex items-center gap-3 px-4 py-[14px] text-lg text-slate-500",children:[t.jsx(dt,{className:"text-slate-300"}),"문의하기"]}),onClick:()=>{B()}},{label:t.jsxs("p",{className:"flex items-center gap-3 px-4 py-[14px] text-lg text-slate-500",children:[t.jsx(tt,{className:"text-slate-300"}),"취소규정"]}),onClick:()=>{x(!0),b(!h)}}],[h,b]=a.useState(!1),[d,x]=a.useState(!1),[$,O]=a.useState("bottom"),[j,S]=a.useState([]),Y=()=>{x(!1)},z=a.useCallback(async()=>{try{const f=await xe.get("/api/booking?page=0",{headers:{Authorization:`Bearer ${c}`}});console.log("예약 목록",f.data);const D=f.data;D.code==="200 성공"&&S(D.data)}catch(f){console.log("예약 목록 불러오기 실패",f)}},[]);a.useEffect(()=>{z()},[]);const B=async()=>{const f="/api/chat-room",D={strfId:i,title:s,bookingId:v};console.log("채팅방 생성 요청",D);try{const M=await xe.post(f,D,{headers:{Authorization:`Bearer ${c}`}});console.log("채팅방 생성",M.data);const L=M.data;L&&k(`/chatroom?roomId=${L.data}`)}catch(M){console.log("채팅방 생성",M),re.error("채팅방 생성에 실패했습니다.")}},E=async f=>{var D,M,L;try{const R=await lt.patch("/api/booking",{bookingId:f});R.data.data==="50퍼센트 환불 완료"?(re.success("50퍼센트 환불 완료"),(D=e.getBookings)==null||D.call(e)):R.data.data==="70퍼센트 환불 완료"?(re.success("70퍼센트 환불 완료"),(M=e.getBookings)==null||M.call(e)):R.data.data==="100퍼센트 환불 완료"?(re.success("100퍼센트 환불 완료"),(L=e.getBookings)==null||L.call(e)):R.data.data==="환불 가능 기간이 아닙니다."&&re.error("환불 가능 기간이 아닙니다."),z()}catch(R){console.log("예약 취소 에러:",R)}};ee().format("YYYY.MM.DD");const _=f=>{switch(f){case 0:return"결제완료";case 1:return"예약확정";case 2:return"이용완료";case 3:return"취소완료"}},W=f=>{switch(f){case 0:return"bg-[rgba(255,253,204,0.6)] text-[#FFCC00]";case 1:return"bg-[rgba(165,238,254,0.3)] text-primary";case 2:return"bg-slate-100 text-slate-400";case 3:return"bg-[rgba(253,180,161,0.3)] text-secondary3"}},F=f=>{switch(f){case 0:case 1:return t.jsxs(t.Fragment,{children:[t.jsx(oe,{onClick:()=>k(`/contents/index?strfId=${i}`),className:"w-full h-auto py-3 rounded-lg text-base font-semibold text-slate-700",children:"상세보기"}),t.jsx(oe,{onClick:()=>{E(e.data.bookingId),z()},className:"w-full h-auto py-3 rounded-lg text-base font-semibold text-slate-700",children:"예약 취소"})]});case 2:return t.jsxs(t.Fragment,{children:[t.jsx(oe,{onClick:()=>k(`/contents/index?strfId=${i}`),className:"w-full h-auto py-3 rounded-lg text-base font-semibold text-slate-700",children:"상세보기"}),t.jsx(oe,{type:"primary",className:"w-full h-auto py-3 rounded-lg text-base font-semibold  text-white bg-primary",children:"리뷰작성"})]});case 3:return t.jsx(t.Fragment,{children:t.jsx(oe,{onClick:()=>k(`/contents/index?strfId=${i}`),className:"w-full h-auto py-3 rounded-lg text-base font-semibold text-slate-700",children:"상세보기"})})}};return t.jsxs("div",{className:"w-full px-4 py-6 flex flex-col gap-4 border-b-[10px] border-slate-100 last:border-none",children:[t.jsx("div",{className:`w-fit flex items-center justify-center px-2 py-1 text-xs font-semibold ${W(l)}`,children:t.jsx("p",{children:_(l)})}),t.jsx("div",{children:t.jsx("h3",{className:"text-xl font-semibold text-slate-700",children:s})}),t.jsxs("div",{className:"flex gap-4",children:[t.jsx("div",{className:"w-20 h-20 rounded-2xl overflow-hidden bg-slate-100",children:t.jsx("img",{src:r?`${Qe}/${i}/${r}`:"",alt:s||"",className:"w-full h-full  aspect-square object-cover"})}),t.jsxs("div",{className:"flex flex-col gap-3",children:[t.jsxs("div",{className:"flex items-start gap-2 text-sm text-slate-700",children:[t.jsx("h4",{className:"whitespace-nowrap text-slate-400 font-semibold text-sm ",children:"이용일시"}),t.jsx("p",{className:"text-base text-slate-700 tracking-tight break-all",children:t.jsxs("span",{children:[ee(n,"YYYY-MM-DD").format("YYYY.MM.DD ddd")," ~ ",ee(o,"YYYY-MM-DD").format("YYYY.MM.DD ddd")]})})]}),t.jsxs("div",{className:"flex items-center gap-3 text-base text-slate-700",children:[t.jsx("h4",{className:"whitespace-nowrap text-slate-400 font-semibold text-sm",children:"예약일시"}),t.jsx("p",{className:"text-base text-slate-700 tracking-tight",children:ee(N,"YYYY-MM-DD").format("YYYY.MM.DD ddd")})]}),t.jsxs("div",{className:"flex items-center gap-3 text-base text-slate-700",children:[t.jsx("h4",{className:"whitespace-nowrap text-slate-400 font-semibold text-sm tracking-tight",children:"인원"}),t.jsx("p",{className:"text-base text-slate-700 tracking-tight",children:"1명"})]})]})]}),t.jsxs("div",{className:"flex gap-2 items-center",children:[F(l),t.jsx("button",{type:"button",className:"aspect-square h-auto p-3 text-base text-slate-700 rounded-lg border border-slate-300 flex justify-center items-center",onClick:()=>b(!0),children:t.jsx(et,{className:"text-[1.5rem]"})})]}),t.jsx(ct,{open:h,onClose:()=>b(!1),actions:m}),t.jsx(_e,{title:"예약 취소 규정",placement:$,closable:!1,onClose:Y,open:d,children:t.jsx(Ot,{})},$)]})},Ne=a.memo(Et),Ye=at({key:"userBookingAtom",default:{data:[]}}),It=nt({key:"userBookingSelector",get:({get:e})=>e(Ye).data.filter(s=>s.state===0||s.state===1)}),Jt=()=>{const e=Oe("accessToken"),{userId:n}=ye(st);ot(Ye),ye(It);const o=De(),s=()=>{o(-1)},[r,u]=a.useState(0),[l,i]=a.useState([]),[p,w]=a.useState([]),[N,v]=a.useState([]),[k,c]=a.useState(!0),[m,h]=a.useState(0),b=a.useCallback(async()=>{try{const d=await xe.get(`/api/booking?page=${m}`,{headers:{Authorization:`Bearer ${e}`}});console.log("예약 목록",d.data);const x=d.data;x.code==="200 성공"&&i(x.data)}catch(d){console.log("예약 목록 불러오기 실패",d)}},[]);return a.useEffect(()=>{b()},[b]),t.jsxs("div",{className:"flex flex-col",children:[t.jsx(rt,{icon:"back",title:"내 예약",onClick:s}),t.jsxs("div",{className:"flex flex-col w-full",children:[t.jsx("div",{children:t.jsx(gt,{className:"custom-tabs-nav custom-tabs-coupon",defaultActiveKey:"1",items:[{label:"예약 목록",key:"1",children:t.jsx(t.Fragment,{children:l==null?void 0:l.filter(d=>d.state===0||d.state===1).map((d,x)=>t.jsx(Ne,{data:d,getBookingList:b},x))})},{label:"예약 완료",key:"2",children:t.jsx(t.Fragment,{children:l==null?void 0:l.filter(d=>d.state===2||d.state===3).map((d,x)=>t.jsx(Ne,{data:d},x))})}]})}),t.jsx("div",{className:"flex flex-col justify-center items-center"}),t.jsx(it,{})]})]})};export{Jt as default};
