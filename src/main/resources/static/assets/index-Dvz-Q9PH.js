import{r as o,aq as ne,at as ce,bB as it,o as E,bh as ze,_ as D,cK as st,av as ct,as as be,ad as he,au as oe,ar as lt,bA as Fe,z as ke,aw as xe,v as ut,w as Ze,bX as we,cm as ft,q as Ge,az as mt,D as vt,ao as Ue,bI as Ke,bf as dt,cL as gt}from"./index-DH-Wz4Mj.js";import{D as pt,g as ht}from"./index-C3h-G8YL.js";import{a as Ce,R as wt}from"./addEventListener-BBQjqPbJ.js";import{P as Ct}from"./Portal-65BEOyHw.js";import{g as Pe}from"./motion-DegXBH0O.js";import{R as St}from"./RightOutlined-D2ucT0M2.js";import{i as bt}from"./fade-BkDnt_Sp.js";import{i as xt}from"./zoom-U1mf4JTG.js";import{u as It}from"./useLocale-DoMzA6HJ.js";function qe(){var t=document.documentElement.clientWidth,e=window.innerHeight||document.documentElement.clientHeight;return{width:t,height:e}}function yt(t){var e=t.getBoundingClientRect(),n=document.documentElement;return{left:e.left+(window.pageXOffset||n.scrollLeft)-(n.clientLeft||document.body.clientLeft||0),top:e.top+(window.pageYOffset||n.scrollTop)-(n.clientTop||document.body.clientTop||0)}}var Ie=o.createContext(null),Rt=function(e){var n=e.visible,r=e.maskTransitionName,a=e.getContainer,i=e.prefixCls,f=e.rootClassName,s=e.icons,u=e.countRender,m=e.showSwitch,g=e.showProgress,c=e.current,S=e.transform,d=e.count,h=e.scale,b=e.minScale,R=e.maxScale,C=e.closeIcon,O=e.onActive,M=e.onClose,N=e.onZoomIn,l=e.onZoomOut,x=e.onRotateRight,p=e.onRotateLeft,v=e.onFlipX,I=e.onFlipY,y=e.onReset,w=e.toolbarRender,P=e.zIndex,A=e.image,T=o.useContext(Ie),Y=s.rotateLeft,B=s.rotateRight,V=s.zoomIn,W=s.zoomOut,q=s.close,H=s.left,F=s.right,Q=s.flipX,G=s.flipY,ie="".concat(i,"-operations-operation");o.useEffect(function(){var $=function(z){z.keyCode===ze.ESC&&M()};return n&&window.addEventListener("keydown",$),function(){window.removeEventListener("keydown",$)}},[n]);var J=function(_,z){_.preventDefault(),_.stopPropagation(),O(z)},k=o.useCallback(function($){var _=$.type,z=$.disabled,Z=$.onClick,j=$.icon;return o.createElement("div",{key:_,className:ne(ie,"".concat(i,"-operations-operation-").concat(_),ce({},"".concat(i,"-operations-operation-disabled"),!!z)),onClick:Z},j)},[ie,i]),se=m?k({icon:H,onClick:function(_){return J(_,-1)},type:"prev",disabled:c===0}):void 0,ee=m?k({icon:F,onClick:function(_){return J(_,1)},type:"next",disabled:c===d-1}):void 0,U=k({icon:G,onClick:I,type:"flipY"}),L=k({icon:Q,onClick:v,type:"flipX"}),re=k({icon:Y,onClick:p,type:"rotateLeft"}),X=k({icon:B,onClick:x,type:"rotateRight"}),K=k({icon:W,onClick:l,type:"zoomOut",disabled:h<=b}),ae=k({icon:V,onClick:N,type:"zoomIn",disabled:h===R}),le=o.createElement("div",{className:"".concat(i,"-operations")},U,L,re,X,K,ae);return o.createElement(it,{visible:n,motionName:r},function($){var _=$.className,z=$.style;return o.createElement(Ct,{open:!0,getContainer:a??document.body},o.createElement("div",{className:ne("".concat(i,"-operations-wrapper"),_,f),style:E(E({},z),{},{zIndex:P})},C===null?null:o.createElement("button",{className:"".concat(i,"-close"),onClick:M},C||q),m&&o.createElement(o.Fragment,null,o.createElement("div",{className:ne("".concat(i,"-switch-left"),ce({},"".concat(i,"-switch-left-disabled"),c===0)),onClick:function(j){return J(j,-1)}},H),o.createElement("div",{className:ne("".concat(i,"-switch-right"),ce({},"".concat(i,"-switch-right-disabled"),c===d-1)),onClick:function(j){return J(j,1)}},F)),o.createElement("div",{className:"".concat(i,"-footer")},g&&o.createElement("div",{className:"".concat(i,"-progress")},u?u(c+1,d):"".concat(c+1," / ").concat(d)),w?w(le,E(E({icons:{prevIcon:se,nextIcon:ee,flipYIcon:U,flipXIcon:L,rotateLeftIcon:re,rotateRightIcon:X,zoomOutIcon:K,zoomInIcon:ae},actions:{onActive:O,onFlipY:I,onFlipX:v,onRotateLeft:p,onRotateRight:x,onZoomOut:l,onZoomIn:N,onReset:y,onClose:M},transform:S},T?{current:c,total:d}:{}),{},{image:A})):le)))})},Ee={x:0,y:0,rotate:0,scale:1,flipX:!1,flipY:!1};function Mt(t,e,n,r){var a=o.useRef(null),i=o.useRef([]),f=o.useState(Ee),s=D(f,2),u=s[0],m=s[1],g=function(h){m(Ee),st(Ee,u)||r==null||r({transform:Ee,action:h})},c=function(h,b){a.current===null&&(i.current=[],a.current=ct(function(){m(function(R){var C=R;return i.current.forEach(function(O){C=E(E({},C),O)}),a.current=null,r==null||r({transform:C,action:b}),C})})),i.current.push(E(E({},u),h))},S=function(h,b,R,C,O){var M=t.current,N=M.width,l=M.height,x=M.offsetWidth,p=M.offsetHeight,v=M.offsetLeft,I=M.offsetTop,y=h,w=u.scale*h;w>n?(w=n,y=n/u.scale):w<e&&(w=O?w:e,y=w/u.scale);var P=R??innerWidth/2,A=C??innerHeight/2,T=y-1,Y=T*N*.5,B=T*l*.5,V=T*(P-u.x-v),W=T*(A-u.y-I),q=u.x-(V-Y),H=u.y-(W-B);if(h<1&&w===1){var F=x*w,Q=p*w,G=qe(),ie=G.width,J=G.height;F<=ie&&Q<=J&&(q=0,H=0)}c({x:q,y:H,scale:w},b)};return{transform:u,resetTransform:g,updateTransform:c,dispatchZoomChange:S}}function He(t,e,n,r){var a=e+n,i=(n-r)/2;if(n>r){if(e>0)return ce({},t,i);if(e<0&&a<r)return ce({},t,-i)}else if(e<0||a>r)return ce({},t,e<0?i:-i);return{}}function Qe(t,e,n,r){var a=qe(),i=a.width,f=a.height,s=null;return t<=i&&e<=f?s={x:0,y:0}:(t>i||e>f)&&(s=E(E({},He("x",n,t,i)),He("y",r,e,f))),s}var Se=1,Nt=1;function Et(t,e,n,r,a,i,f){var s=a.rotate,u=a.scale,m=a.x,g=a.y,c=o.useState(!1),S=D(c,2),d=S[0],h=S[1],b=o.useRef({diffX:0,diffY:0,transformX:0,transformY:0}),R=function(l){!e||l.button!==0||(l.preventDefault(),l.stopPropagation(),b.current={diffX:l.pageX-m,diffY:l.pageY-g,transformX:m,transformY:g},h(!0))},C=function(l){n&&d&&i({x:l.pageX-b.current.diffX,y:l.pageY-b.current.diffY},"move")},O=function(){if(n&&d){h(!1);var l=b.current,x=l.transformX,p=l.transformY,v=m!==x&&g!==p;if(!v)return;var I=t.current.offsetWidth*u,y=t.current.offsetHeight*u,w=t.current.getBoundingClientRect(),P=w.left,A=w.top,T=s%180!==0,Y=Qe(T?y:I,T?I:y,P,A);Y&&i(E({},Y),"dragRebound")}},M=function(l){if(!(!n||l.deltaY==0)){var x=Math.abs(l.deltaY/100),p=Math.min(x,Nt),v=Se+p*r;l.deltaY>0&&(v=Se/v),f(v,"wheel",l.clientX,l.clientY)}};return o.useEffect(function(){var N,l,x,p;if(e){x=Ce(window,"mouseup",O,!1),p=Ce(window,"mousemove",C,!1);try{window.top!==window.self&&(N=Ce(window.top,"mouseup",O,!1),l=Ce(window.top,"mousemove",C,!1))}catch{}}return function(){var v,I,y,w;(v=x)===null||v===void 0||v.remove(),(I=p)===null||I===void 0||I.remove(),(y=N)===null||y===void 0||y.remove(),(w=l)===null||w===void 0||w.remove()}},[n,d,m,g,s,e]),{isMoving:d,onMouseDown:R,onMouseMove:C,onMouseUp:O,onWheel:M}}function Pt(t){return new Promise(function(e){if(!t){e(!1);return}var n=document.createElement("img");n.onerror=function(){return e(!1)},n.onload=function(){return e(!0)},n.src=t})}function Je(t){var e=t.src,n=t.isCustomPlaceholder,r=t.fallback,a=o.useState(n?"loading":"normal"),i=D(a,2),f=i[0],s=i[1],u=o.useRef(!1),m=f==="error";o.useEffect(function(){var d=!0;return Pt(e).then(function(h){!h&&d&&s("error")}),function(){d=!1}},[e]),o.useEffect(function(){n&&!u.current?s("loading"):m&&s("normal")},[e]);var g=function(){s("normal")},c=function(h){u.current=!1,f==="loading"&&h!==null&&h!==void 0&&h.complete&&(h.naturalWidth||h.naturalHeight)&&(u.current=!0,g())},S=m&&r?{src:r}:{onLoad:g,src:e};return[c,S,f]}function Oe(t,e){var n=t.x-e.x,r=t.y-e.y;return Math.hypot(n,r)}function Ot(t,e,n,r){var a=Oe(t,n),i=Oe(e,r);if(a===0&&i===0)return[t.x,t.y];var f=a/(a+i),s=t.x+f*(e.x-t.x),u=t.y+f*(e.y-t.y);return[s,u]}function Tt(t,e,n,r,a,i,f){var s=a.rotate,u=a.scale,m=a.x,g=a.y,c=o.useState(!1),S=D(c,2),d=S[0],h=S[1],b=o.useRef({point1:{x:0,y:0},point2:{x:0,y:0},eventType:"none"}),R=function(l){b.current=E(E({},b.current),l)},C=function(l){if(e){l.stopPropagation(),h(!0);var x=l.touches,p=x===void 0?[]:x;p.length>1?R({point1:{x:p[0].clientX,y:p[0].clientY},point2:{x:p[1].clientX,y:p[1].clientY},eventType:"touchZoom"}):R({point1:{x:p[0].clientX-m,y:p[0].clientY-g},eventType:"move"})}},O=function(l){var x=l.touches,p=x===void 0?[]:x,v=b.current,I=v.point1,y=v.point2,w=v.eventType;if(p.length>1&&w==="touchZoom"){var P={x:p[0].clientX,y:p[0].clientY},A={x:p[1].clientX,y:p[1].clientY},T=Ot(I,y,P,A),Y=D(T,2),B=Y[0],V=Y[1],W=Oe(P,A)/Oe(I,y);f(W,"touchZoom",B,V,!0),R({point1:P,point2:A,eventType:"touchZoom"})}else w==="move"&&(i({x:p[0].clientX-I.x,y:p[0].clientY-I.y},"move"),R({eventType:"move"}))},M=function(){if(n){if(d&&h(!1),R({eventType:"none"}),r>u)return i({x:0,y:0,scale:r},"touchZoom");var l=t.current.offsetWidth*u,x=t.current.offsetHeight*u,p=t.current.getBoundingClientRect(),v=p.left,I=p.top,y=s%180!==0,w=Qe(y?x:l,y?l:x,v,I);w&&i(E({},w),"dragRebound")}};return o.useEffect(function(){var N;return n&&e&&(N=Ce(window,"touchmove",function(l){return l.preventDefault()},{passive:!1})),function(){var l;(l=N)===null||l===void 0||l.remove()}},[n,e]),{isTouching:d,onTouchStart:C,onTouchMove:O,onTouchEnd:M}}var Lt=["fallback","src","imgRef"],$t=["prefixCls","src","alt","imageInfo","fallback","movable","onClose","visible","icons","rootClassName","closeIcon","getContainer","current","count","countRender","scaleStep","minScale","maxScale","transitionName","maskTransitionName","imageRender","imgCommonProps","toolbarRender","onTransform","onChange"],_t=function(e){var n=e.fallback,r=e.src,a=e.imgRef,i=be(e,Lt),f=Je({src:r,fallback:n}),s=D(f,2),u=s[0],m=s[1];return he.createElement("img",oe({ref:function(c){a.current=c,u(c)}},i,m))},et=function(e){var n=e.prefixCls,r=e.src,a=e.alt,i=e.imageInfo,f=e.fallback,s=e.movable,u=s===void 0?!0:s,m=e.onClose,g=e.visible,c=e.icons,S=c===void 0?{}:c,d=e.rootClassName,h=e.closeIcon,b=e.getContainer,R=e.current,C=R===void 0?0:R,O=e.count,M=O===void 0?1:O,N=e.countRender,l=e.scaleStep,x=l===void 0?.5:l,p=e.minScale,v=p===void 0?1:p,I=e.maxScale,y=I===void 0?50:I,w=e.transitionName,P=w===void 0?"zoom":w,A=e.maskTransitionName,T=A===void 0?"fade":A,Y=e.imageRender,B=e.imgCommonProps,V=e.toolbarRender,W=e.onTransform,q=e.onChange,H=be(e,$t),F=o.useRef(),Q=o.useContext(Ie),G=Q&&M>1,ie=Q&&M>=1,J=o.useState(!0),k=D(J,2),se=k[0],ee=k[1],U=Mt(F,v,y,W),L=U.transform,re=U.resetTransform,X=U.updateTransform,K=U.dispatchZoomChange,ae=Et(F,u,g,x,L,X,K),le=ae.isMoving,$=ae.onMouseDown,_=ae.onWheel,z=Tt(F,u,g,v,L,X,K),Z=z.isTouching,j=z.onTouchStart,me=z.onTouchMove,pe=z.onTouchEnd,ue=L.rotate,fe=L.scale,Te=ne(ce({},"".concat(n,"-moving"),le));o.useEffect(function(){se||ee(!0)},[se]);var Le=function(){re("close")},$e=function(){K(Se+x,"zoomIn")},ve=function(){K(Se/(Se+x),"zoomOut")},de=function(){X({rotate:ue+90},"rotateRight")},ye=function(){X({rotate:ue-90},"rotateLeft")},Re=function(){X({flipX:!L.flipX},"flipX")},Me=function(){X({flipY:!L.flipY},"flipY")},ot=function(){re("reset")},_e=function(ge){var Ne=C+ge;!Number.isInteger(Ne)||Ne<0||Ne>M-1||(ee(!1),re(ge<0?"prev":"next"),q==null||q(Ne,C))},rt=function(ge){!g||!G||(ge.keyCode===ze.LEFT?_e(-1):ge.keyCode===ze.RIGHT&&_e(1))},at=function(ge){g&&(fe!==1?X({x:0,y:0,scale:1},"doubleClick"):K(Se+x,"doubleClick",ge.clientX,ge.clientY))};o.useEffect(function(){var te=Ce(window,"keydown",rt,!1);return function(){te.remove()}},[g,G,C]);var Ye=he.createElement(_t,oe({},B,{width:e.width,height:e.height,imgRef:F,className:"".concat(n,"-img"),alt:a,style:{transform:"translate3d(".concat(L.x,"px, ").concat(L.y,"px, 0) scale3d(").concat(L.flipX?"-":"").concat(fe,", ").concat(L.flipY?"-":"").concat(fe,", 1) rotate(").concat(ue,"deg)"),transitionDuration:(!se||Z)&&"0s"},fallback:f,src:r,onWheel:_,onMouseDown:$,onDoubleClick:at,onTouchStart:j,onTouchMove:me,onTouchEnd:pe,onTouchCancel:pe})),Xe=E({url:r,alt:a},i);return he.createElement(he.Fragment,null,he.createElement(pt,oe({transitionName:P,maskTransitionName:T,closable:!1,keyboard:!0,prefixCls:n,onClose:m,visible:g,classNames:{wrapper:Te},rootClassName:d,getContainer:b},H,{afterClose:Le}),he.createElement("div",{className:"".concat(n,"-img-wrapper")},Y?Y(Ye,E({transform:L,image:Xe},Q?{current:C}:{})):Ye)),he.createElement(Rt,{visible:g,transform:L,maskTransitionName:T,closeIcon:h,getContainer:b,prefixCls:n,rootClassName:d,icons:S,countRender:N,showSwitch:G,showProgress:ie,current:C,count:M,scale:fe,minScale:v,maxScale:y,toolbarRender:V,onActive:_e,onZoomIn:$e,onZoomOut:ve,onRotateRight:de,onRotateLeft:ye,onFlipX:Re,onFlipY:Me,onClose:m,onReset:ot,zIndex:H.zIndex!==void 0?H.zIndex+1:void 0,image:Xe}))},Ae=["crossOrigin","decoding","draggable","loading","referrerPolicy","sizes","srcSet","useMap","alt"];function zt(t){var e=o.useState({}),n=D(e,2),r=n[0],a=n[1],i=o.useCallback(function(s,u){return a(function(m){return E(E({},m),{},ce({},s,u))}),function(){a(function(m){var g=E({},m);return delete g[s],g})}},[]),f=o.useMemo(function(){return t?t.map(function(s){if(typeof s=="string")return{data:{src:s}};var u={};return Object.keys(s).forEach(function(m){["src"].concat(lt(Ae)).includes(m)&&(u[m]=s[m])}),{data:u}}):Object.keys(r).reduce(function(s,u){var m=r[u],g=m.canPreview,c=m.data;return g&&s.push({data:c,id:u}),s},[])},[t,r]);return[f,i,!!t]}var kt=["visible","onVisibleChange","getContainer","current","movable","minScale","maxScale","countRender","closeIcon","onChange","onTransform","toolbarRender","imageRender"],At=["src"],jt=function(e){var n,r=e.previewPrefixCls,a=r===void 0?"rc-image-preview":r,i=e.children,f=e.icons,s=f===void 0?{}:f,u=e.items,m=e.preview,g=e.fallback,c=Fe(m)==="object"?m:{},S=c.visible,d=c.onVisibleChange,h=c.getContainer,b=c.current,R=c.movable,C=c.minScale,O=c.maxScale,M=c.countRender,N=c.closeIcon,l=c.onChange,x=c.onTransform,p=c.toolbarRender,v=c.imageRender,I=be(c,kt),y=zt(u),w=D(y,3),P=w[0],A=w[1],T=w[2],Y=ke(0,{value:b}),B=D(Y,2),V=B[0],W=B[1],q=o.useState(!1),H=D(q,2),F=H[0],Q=H[1],G=((n=P[V])===null||n===void 0?void 0:n.data)||{},ie=G.src,J=be(G,At),k=ke(!!S,{value:S,onChange:function(Z,j){d==null||d(Z,j,V)}}),se=D(k,2),ee=se[0],U=se[1],L=o.useState(null),re=D(L,2),X=re[0],K=re[1],ae=o.useCallback(function(z,Z,j,me){var pe=T?P.findIndex(function(ue){return ue.data.src===Z}):P.findIndex(function(ue){return ue.id===z});W(pe<0?0:pe),U(!0),K({x:j,y:me}),Q(!0)},[P,T]);o.useEffect(function(){ee?F||W(0):Q(!1)},[ee]);var le=function(Z,j){W(Z),l==null||l(Z,j)},$=function(){U(!1),K(null)},_=o.useMemo(function(){return{register:A,onPreview:ae}},[A,ae]);return o.createElement(Ie.Provider,{value:_},i,o.createElement(et,oe({"aria-hidden":!ee,movable:R,visible:ee,prefixCls:a,closeIcon:N,onClose:$,mousePosition:X,imgCommonProps:J,src:ie,fallback:g,icons:s,minScale:C,maxScale:O,getContainer:h,current:V,count:P.length,countRender:M,onTransform:x,toolbarRender:p,imageRender:v,onChange:le},I)))},Be=0;function Dt(t,e){var n=o.useState(function(){return Be+=1,String(Be)}),r=D(n,1),a=r[0],i=o.useContext(Ie),f={data:e,canPreview:t};return o.useEffect(function(){if(i)return i.register(a,f)},[]),o.useEffect(function(){i&&i.register(a,f)},[t,e]),a}var Yt=["src","alt","onPreviewClose","prefixCls","previewPrefixCls","placeholder","fallback","width","height","style","preview","className","onClick","onError","wrapperClassName","wrapperStyle","rootClassName"],Xt=["src","visible","onVisibleChange","getContainer","mask","maskClassName","movable","icons","scaleStep","minScale","maxScale","imageRender","toolbarRender"],De=function(e){var n=e.src,r=e.alt,a=e.onPreviewClose,i=e.prefixCls,f=i===void 0?"rc-image":i,s=e.previewPrefixCls,u=s===void 0?"".concat(f,"-preview"):s,m=e.placeholder,g=e.fallback,c=e.width,S=e.height,d=e.style,h=e.preview,b=h===void 0?!0:h,R=e.className,C=e.onClick,O=e.onError,M=e.wrapperClassName,N=e.wrapperStyle,l=e.rootClassName,x=be(e,Yt),p=m&&m!==!0,v=Fe(b)==="object"?b:{},I=v.src,y=v.visible,w=y===void 0?void 0:y,P=v.onVisibleChange,A=P===void 0?a:P,T=v.getContainer,Y=T===void 0?void 0:T,B=v.mask,V=v.maskClassName,W=v.movable,q=v.icons,H=v.scaleStep,F=v.minScale,Q=v.maxScale,G=v.imageRender,ie=v.toolbarRender,J=be(v,Xt),k=I??n,se=ke(!!w,{value:w,onChange:A}),ee=D(se,2),U=ee[0],L=ee[1],re=Je({src:n,isCustomPlaceholder:p,fallback:g}),X=D(re,3),K=X[0],ae=X[1],le=X[2],$=o.useState(null),_=D($,2),z=_[0],Z=_[1],j=o.useContext(Ie),me=!!b,pe=function(){L(!1),Z(null)},ue=ne(f,M,l,ce({},"".concat(f,"-error"),le==="error")),fe=o.useMemo(function(){var ve={};return Ae.forEach(function(de){e[de]!==void 0&&(ve[de]=e[de])}),ve},Ae.map(function(ve){return e[ve]})),Te=o.useMemo(function(){return E(E({},fe),{},{src:k})},[k,fe]),Le=Dt(me,Te),$e=function(de){var ye=yt(de.target),Re=ye.left,Me=ye.top;j?j.onPreview(Le,k,Re,Me):(Z({x:Re,y:Me}),L(!0)),C==null||C(de)};return o.createElement(o.Fragment,null,o.createElement("div",oe({},x,{className:ue,onClick:me?$e:C,style:E({width:c,height:S},N)}),o.createElement("img",oe({},fe,{className:ne("".concat(f,"-img"),ce({},"".concat(f,"-img-placeholder"),m===!0),R),style:E({height:S},d),ref:K},ae,{width:c,height:S,onError:O})),le==="loading"&&o.createElement("div",{"aria-hidden":"true",className:"".concat(f,"-placeholder")},m),B&&me&&o.createElement("div",{className:ne("".concat(f,"-mask"),V),style:{display:(d==null?void 0:d.display)==="none"?"none":void 0}},B)),!j&&me&&o.createElement(et,oe({"aria-hidden":!U,visible:U,prefixCls:u,onClose:pe,mousePosition:z,src:k,alt:r,imageInfo:{width:c,height:S},fallback:g,getContainer:Y,icons:q,movable:W,scaleStep:H,minScale:F,maxScale:Q,rootClassName:l,imageRender:G,imgCommonProps:fe,toolbarRender:ie},J)))};De.PreviewGroup=jt;var Zt={icon:{tag:"svg",attrs:{viewBox:"64 64 896 896",focusable:"false"},children:[{tag:"defs",attrs:{},children:[{tag:"style",attrs:{}}]},{tag:"path",attrs:{d:"M672 418H144c-17.7 0-32 14.3-32 32v414c0 17.7 14.3 32 32 32h528c17.7 0 32-14.3 32-32V450c0-17.7-14.3-32-32-32zm-44 402H188V494h440v326z"}},{tag:"path",attrs:{d:"M819.3 328.5c-78.8-100.7-196-153.6-314.6-154.2l-.2-64c0-6.5-7.6-10.1-12.6-6.1l-128 101c-4 3.1-3.9 9.1 0 12.3L492 318.6c5.1 4 12.7.4 12.6-6.1v-63.9c12.9.1 25.9.9 38.8 2.5 42.1 5.2 82.1 18.2 119 38.7 38.1 21.2 71.2 49.7 98.4 84.3 27.1 34.7 46.7 73.7 58.1 115.8a325.95 325.95 0 016.5 140.9h74.9c14.8-103.6-11.3-213-81-302.3z"}}]},name:"rotate-left",theme:"outlined"},Ht=function(e,n){return o.createElement(xe,oe({},e,{ref:n,icon:Zt}))},Bt=o.forwardRef(Ht),Vt={icon:{tag:"svg",attrs:{viewBox:"64 64 896 896",focusable:"false"},children:[{tag:"defs",attrs:{},children:[{tag:"style",attrs:{}}]},{tag:"path",attrs:{d:"M480.5 251.2c13-1.6 25.9-2.4 38.8-2.5v63.9c0 6.5 7.5 10.1 12.6 6.1L660 217.6c4-3.2 4-9.2 0-12.3l-128-101c-5.1-4-12.6-.4-12.6 6.1l-.2 64c-118.6.5-235.8 53.4-314.6 154.2A399.75 399.75 0 00123.5 631h74.9c-.9-5.3-1.7-10.7-2.4-16.1-5.1-42.1-2.1-84.1 8.9-124.8 11.4-42.2 31-81.1 58.1-115.8 27.2-34.7 60.3-63.2 98.4-84.3 37-20.6 76.9-33.6 119.1-38.8z"}},{tag:"path",attrs:{d:"M880 418H352c-17.7 0-32 14.3-32 32v414c0 17.7 14.3 32 32 32h528c17.7 0 32-14.3 32-32V450c0-17.7-14.3-32-32-32zm-44 402H396V494h440v326z"}}]},name:"rotate-right",theme:"outlined"},Wt=function(e,n){return o.createElement(xe,oe({},e,{ref:n,icon:Vt}))},Ft=o.forwardRef(Wt),Gt={icon:{tag:"svg",attrs:{viewBox:"64 64 896 896",focusable:"false"},children:[{tag:"path",attrs:{d:"M847.9 592H152c-4.4 0-8 3.6-8 8v60c0 4.4 3.6 8 8 8h605.2L612.9 851c-4.1 5.2-.4 13 6.3 13h72.5c4.9 0 9.5-2.2 12.6-6.1l168.8-214.1c16.5-21 1.6-51.8-25.2-51.8zM872 356H266.8l144.3-183c4.1-5.2.4-13-6.3-13h-72.5c-4.9 0-9.5 2.2-12.6 6.1L150.9 380.2c-16.5 21-1.6 51.8 25.1 51.8h696c4.4 0 8-3.6 8-8v-60c0-4.4-3.6-8-8-8z"}}]},name:"swap",theme:"outlined"},Ut=function(e,n){return o.createElement(xe,oe({},e,{ref:n,icon:Gt}))},Ve=o.forwardRef(Ut),Kt={icon:{tag:"svg",attrs:{viewBox:"64 64 896 896",focusable:"false"},children:[{tag:"path",attrs:{d:"M637 443H519V309c0-4.4-3.6-8-8-8h-60c-4.4 0-8 3.6-8 8v134H325c-4.4 0-8 3.6-8 8v60c0 4.4 3.6 8 8 8h118v134c0 4.4 3.6 8 8 8h60c4.4 0 8-3.6 8-8V519h118c4.4 0 8-3.6 8-8v-60c0-4.4-3.6-8-8-8zm284 424L775 721c122.1-148.9 113.6-369.5-26-509-148-148.1-388.4-148.1-537 0-148.1 148.6-148.1 389 0 537 139.5 139.6 360.1 148.1 509 26l146 146c3.2 2.8 8.3 2.8 11 0l43-43c2.8-2.7 2.8-7.8 0-11zM696 696c-118.8 118.7-311.2 118.7-430 0-118.7-118.8-118.7-311.2 0-430 118.8-118.7 311.2-118.7 430 0 118.7 118.8 118.7 311.2 0 430z"}}]},name:"zoom-in",theme:"outlined"},qt=function(e,n){return o.createElement(xe,oe({},e,{ref:n,icon:Kt}))},Qt=o.forwardRef(qt),Jt={icon:{tag:"svg",attrs:{viewBox:"64 64 896 896",focusable:"false"},children:[{tag:"path",attrs:{d:"M637 443H325c-4.4 0-8 3.6-8 8v60c0 4.4 3.6 8 8 8h312c4.4 0 8-3.6 8-8v-60c0-4.4-3.6-8-8-8zm284 424L775 721c122.1-148.9 113.6-369.5-26-509-148-148.1-388.4-148.1-537 0-148.1 148.6-148.1 389 0 537 139.5 139.6 360.1 148.1 509 26l146 146c3.2 2.8 8.3 2.8 11 0l43-43c2.8-2.7 2.8-7.8 0-11zM696 696c-118.8 118.7-311.2 118.7-430 0-118.7-118.8-118.7-311.2 0-430 118.8-118.7 311.2-118.7 430 0 118.7 118.8 118.7 311.2 0 430z"}}]},name:"zoom-out",theme:"outlined"},en=function(e,n){return o.createElement(xe,oe({},e,{ref:n,icon:Jt}))},tn=o.forwardRef(en);const je=t=>({position:t||"absolute",inset:0}),nn=t=>{const{iconCls:e,motionDurationSlow:n,paddingXXS:r,marginXXS:a,prefixCls:i,colorTextLightSolid:f}=t;return{position:"absolute",inset:0,display:"flex",alignItems:"center",justifyContent:"center",color:f,background:new we("#000").setA(.5).toRgbString(),cursor:"pointer",opacity:0,transition:`opacity ${n}`,[`.${i}-mask-info`]:Object.assign(Object.assign({},ft),{padding:`0 ${Ge(r)}`,[e]:{marginInlineEnd:a,svg:{verticalAlign:"baseline"}}})}},on=t=>{const{previewCls:e,modalMaskBg:n,paddingSM:r,marginXL:a,margin:i,paddingLG:f,previewOperationColorDisabled:s,previewOperationHoverColor:u,motionDurationSlow:m,iconCls:g,colorTextLightSolid:c}=t,S=new we(n).setA(.1),d=S.clone().setA(.2);return{[`${e}-footer`]:{position:"fixed",bottom:a,left:{_skip_check_:!0,value:"50%"},display:"flex",flexDirection:"column",alignItems:"center",color:t.previewOperationColor,transform:"translateX(-50%)"},[`${e}-progress`]:{marginBottom:i},[`${e}-close`]:{position:"fixed",top:a,right:{_skip_check_:!0,value:a},display:"flex",color:c,backgroundColor:S.toRgbString(),borderRadius:"50%",padding:r,outline:0,border:0,cursor:"pointer",transition:`all ${m}`,"&:hover":{backgroundColor:d.toRgbString()},[`& > ${g}`]:{fontSize:t.previewOperationSize}},[`${e}-operations`]:{display:"flex",alignItems:"center",padding:`0 ${Ge(f)}`,backgroundColor:S.toRgbString(),borderRadius:100,"&-operation":{marginInlineStart:r,padding:r,cursor:"pointer",transition:`all ${m}`,userSelect:"none",[`&:not(${e}-operations-operation-disabled):hover > ${g}`]:{color:u},"&-disabled":{color:s,cursor:"not-allowed"},"&:first-of-type":{marginInlineStart:0},[`& > ${g}`]:{fontSize:t.previewOperationSize}}}}},rn=t=>{const{modalMaskBg:e,iconCls:n,previewOperationColorDisabled:r,previewCls:a,zIndexPopup:i,motionDurationSlow:f}=t,s=new we(e).setA(.1),u=s.clone().setA(.2);return{[`${a}-switch-left, ${a}-switch-right`]:{position:"fixed",insetBlockStart:"50%",zIndex:t.calc(i).add(1).equal(),display:"flex",alignItems:"center",justifyContent:"center",width:t.imagePreviewSwitchSize,height:t.imagePreviewSwitchSize,marginTop:t.calc(t.imagePreviewSwitchSize).mul(-1).div(2).equal(),color:t.previewOperationColor,background:s.toRgbString(),borderRadius:"50%",transform:"translateY(-50%)",cursor:"pointer",transition:`all ${f}`,userSelect:"none","&:hover":{background:u.toRgbString()},"&-disabled":{"&, &:hover":{color:r,background:"transparent",cursor:"not-allowed",[`> ${n}`]:{cursor:"not-allowed"}}},[`> ${n}`]:{fontSize:t.previewOperationSize}},[`${a}-switch-left`]:{insetInlineStart:t.marginSM},[`${a}-switch-right`]:{insetInlineEnd:t.marginSM}}},an=t=>{const{motionEaseOut:e,previewCls:n,motionDurationSlow:r,componentCls:a}=t;return[{[`${a}-preview-root`]:{[n]:{height:"100%",textAlign:"center",pointerEvents:"none"},[`${n}-body`]:Object.assign(Object.assign({},je()),{overflow:"hidden"}),[`${n}-img`]:{maxWidth:"100%",maxHeight:"70%",verticalAlign:"middle",transform:"scale3d(1, 1, 1)",cursor:"grab",transition:`transform ${r} ${e} 0s`,userSelect:"none","&-wrapper":Object.assign(Object.assign({},je()),{transition:`transform ${r} ${e} 0s`,display:"flex",justifyContent:"center",alignItems:"center","& > *":{pointerEvents:"auto"},"&::before":{display:"inline-block",width:1,height:"50%",marginInlineEnd:-1,content:'""'}})},[`${n}-moving`]:{[`${n}-preview-img`]:{cursor:"grabbing","&-wrapper":{transitionDuration:"0s"}}}}},{[`${a}-preview-root`]:{[`${n}-wrap`]:{zIndex:t.zIndexPopup}}},{[`${a}-preview-operations-wrapper`]:{position:"fixed",zIndex:t.calc(t.zIndexPopup).add(1).equal()},"&":[on(t),rn(t)]}]},sn=t=>{const{componentCls:e}=t;return{[e]:{position:"relative",display:"inline-block",[`${e}-img`]:{width:"100%",height:"auto",verticalAlign:"middle"},[`${e}-img-placeholder`]:{backgroundColor:t.colorBgContainerDisabled,backgroundImage:"url('data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iMTYiIGhlaWdodD0iMTYiIHZpZXdCb3g9IjAgMCAxNiAxNiIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj48cGF0aCBkPSJNMTQuNSAyLjVoLTEzQS41LjUgMCAwIDAgMSAzdjEwYS41LjUgMCAwIDAgLjUuNWgxM2EuNS41IDAgMCAwIC41LS41VjNhLjUuNSAwIDAgMC0uNS0uNXpNNS4yODEgNC43NWExIDEgMCAwIDEgMCAyIDEgMSAwIDAgMSAwLTJ6bTguMDMgNi44M2EuMTI3LjEyNyAwIDAgMS0uMDgxLjAzSDIuNzY5YS4xMjUuMTI1IDAgMCAxLS4wOTYtLjIwN2wyLjY2MS0zLjE1NmEuMTI2LjEyNiAwIDAgMSAuMTc3LS4wMTZsLjAxNi4wMTZMNy4wOCAxMC4wOWwyLjQ3LTIuOTNhLjEyNi4xMjYgMCAwIDEgLjE3Ny0uMDE2bC4wMTUuMDE2IDMuNTg4IDQuMjQ0YS4xMjcuMTI3IDAgMCAxLS4wMi4xNzV6IiBmaWxsPSIjOEM4QzhDIiBmaWxsLXJ1bGU9Im5vbnplcm8iLz48L3N2Zz4=')",backgroundRepeat:"no-repeat",backgroundPosition:"center center",backgroundSize:"30%"},[`${e}-mask`]:Object.assign({},nn(t)),[`${e}-mask:hover`]:{opacity:1},[`${e}-placeholder`]:Object.assign({},je())}}},cn=t=>{const{previewCls:e}=t;return{[`${e}-root`]:xt(t,"zoom"),"&":bt(t,!0)}},ln=t=>({zIndexPopup:t.zIndexPopupBase+80,previewOperationColor:new we(t.colorTextLightSolid).setA(.65).toRgbString(),previewOperationHoverColor:new we(t.colorTextLightSolid).setA(.85).toRgbString(),previewOperationColorDisabled:new we(t.colorTextLightSolid).setA(.25).toRgbString(),previewOperationSize:t.fontSizeIcon*1.5}),tt=ut("Image",t=>{const e=`${t.componentCls}-preview`,n=Ze(t,{previewCls:e,modalMaskBg:new we("#000").setA(.45).toRgbString(),imagePreviewSwitchSize:t.controlHeightLG});return[sn(n),an(n),ht(Ze(n,{componentCls:e})),cn(n)]},ln);var un=function(t,e){var n={};for(var r in t)Object.prototype.hasOwnProperty.call(t,r)&&e.indexOf(r)<0&&(n[r]=t[r]);if(t!=null&&typeof Object.getOwnPropertySymbols=="function")for(var a=0,r=Object.getOwnPropertySymbols(t);a<r.length;a++)e.indexOf(r[a])<0&&Object.prototype.propertyIsEnumerable.call(t,r[a])&&(n[r[a]]=t[r[a]]);return n};const nt={rotateLeft:o.createElement(Bt,null),rotateRight:o.createElement(Ft,null),zoomIn:o.createElement(Qt,null),zoomOut:o.createElement(tn,null),close:o.createElement(mt,null),left:o.createElement(wt,null),right:o.createElement(St,null),flipX:o.createElement(Ve,null),flipY:o.createElement(Ve,{rotate:90})},fn=t=>{var{previewPrefixCls:e,preview:n}=t,r=un(t,["previewPrefixCls","preview"]);const{getPrefixCls:a}=o.useContext(vt),i=a("image",e),f=`${i}-preview`,s=a(),u=Ue(i),[m,g,c]=tt(i,u),[S]=Ke("ImagePreview",typeof n=="object"?n.zIndex:void 0),d=o.useMemo(()=>{var h;if(n===!1)return n;const b=typeof n=="object"?n:{},R=ne(g,c,u,(h=b.rootClassName)!==null&&h!==void 0?h:"");return Object.assign(Object.assign({},b),{transitionName:Pe(s,"zoom",b.transitionName),maskTransitionName:Pe(s,"fade",b.maskTransitionName),rootClassName:R,zIndex:S})},[n]);return m(o.createElement(De.PreviewGroup,Object.assign({preview:d,previewPrefixCls:f,icons:nt},r)))};var We=function(t,e){var n={};for(var r in t)Object.prototype.hasOwnProperty.call(t,r)&&e.indexOf(r)<0&&(n[r]=t[r]);if(t!=null&&typeof Object.getOwnPropertySymbols=="function")for(var a=0,r=Object.getOwnPropertySymbols(t);a<r.length;a++)e.indexOf(r[a])<0&&Object.prototype.propertyIsEnumerable.call(t,r[a])&&(n[r[a]]=t[r[a]]);return n};const mn=t=>{const{prefixCls:e,preview:n,className:r,rootClassName:a,style:i}=t,f=We(t,["prefixCls","preview","className","rootClassName","style"]),{getPrefixCls:s,getPopupContainer:u,className:m,style:g,preview:c}=dt("image"),[S]=It("Image"),d=s("image",e),h=s(),b=Ue(d),[R,C,O]=tt(d,b),M=ne(a,C,O,b),N=ne(r,C,m),[l]=Ke("ImagePreview",typeof n=="object"?n.zIndex:void 0),x=o.useMemo(()=>{if(n===!1)return n;const v=typeof n=="object"?n:{},{getContainer:I,closeIcon:y,rootClassName:w}=v,P=We(v,["getContainer","closeIcon","rootClassName"]);return Object.assign(Object.assign({mask:o.createElement("div",{className:`${d}-mask-info`},o.createElement(gt,null),S==null?void 0:S.preview),icons:nt},P),{rootClassName:ne(M,w),getContainer:I??u,transitionName:Pe(h,"zoom",v.transitionName),maskTransitionName:Pe(h,"fade",v.maskTransitionName),zIndex:l,closeIcon:y??(c==null?void 0:c.closeIcon)})},[n,S,c==null?void 0:c.closeIcon]),p=Object.assign(Object.assign({},g),i);return R(o.createElement(De,Object.assign({prefixCls:d,preview:x,rootClassName:M,className:N,style:p},f)))};mn.PreviewGroup=fn;export{mn as I};
