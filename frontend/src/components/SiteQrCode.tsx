import { useMemo, useState } from 'react'

import { QRCodeSVG } from 'qrcode.react'

function resolveSiteUrl() {
  if (typeof window === 'undefined') {
    return ''
  }

  return window.location.origin
}

export function SiteQrCode() {
  const [isFullscreen, setIsFullscreen] = useState(false)
  const siteUrl = useMemo(() => resolveSiteUrl(), [])

  if (!siteUrl) {
    return null
  }

  return (
    <>
      <button
        type="button"
        className="group hidden items-center gap-3 rounded-2xl border border-white/10 bg-white/5 px-2 py-2 text-left transition hover:border-white/20 hover:bg-white/10 md:flex"
        onClick={() => setIsFullscreen(true)}
        aria-label="Prikaži QR kod stranice preko cijelog ekrana"
      >
        <div className="overflow-hidden rounded-xl border border-white/15 bg-white p-1.5 shadow-sm">
          <QRCodeSVG value={siteUrl} size={54} bgColor="#ffffff" fgColor="#020617" includeMargin={false} />
        </div>
        <div className="pr-1">
          <p className="text-[10px] font-semibold uppercase tracking-[0.28em] text-slate-400">QR</p>
          <p className="text-sm font-semibold text-white">Otvori puni prikaz</p>
        </div>
      </button>

      {isFullscreen ? (
        <div
          className="fixed inset-0 z-[100] flex items-center justify-center bg-slate-950/95 p-6 backdrop-blur-sm"
          onClick={() => setIsFullscreen(false)}
        >
          <div
            className="relative flex w-full max-w-xl flex-col items-center rounded-[2rem] border border-white/10 bg-slate-900 px-6 py-8 text-center shadow-[0_40px_120px_rgba(15,23,42,0.55)]"
            onClick={(event) => event.stopPropagation()}
          >
            <button
              type="button"
              className="absolute right-4 top-4 rounded-full border border-white/10 bg-white/5 px-3 py-1.5 text-sm font-semibold text-slate-200 transition hover:bg-white/10"
              onClick={() => setIsFullscreen(false)}
            >
              Zatvori
            </button>

            <p className="text-xs font-semibold uppercase tracking-[0.32em] text-slate-400">QR kod stranice</p>
            <h2 className="mt-3 font-display text-3xl text-white sm:text-4xl">Skeniraj za otvaranje aplikacije</h2>
            <p className="mt-3 max-w-md text-sm text-slate-300 sm:text-base">Kod vodi na trenutnu adresu aplikacije i pogodan je za brzo otvaranje na telefonu.</p>

            <div className="mt-8 rounded-[2rem] bg-white p-5 shadow-2xl">
              <QRCodeSVG value={siteUrl} size={320} bgColor="#ffffff" fgColor="#020617" includeMargin={true} />
            </div>

            <a
              href={siteUrl}
              className="mt-6 break-all text-sm font-semibold text-orange-300 underline decoration-orange-500/50 underline-offset-4 hover:text-orange-200"
              target="_blank"
              rel="noreferrer"
            >
              {siteUrl}
            </a>
          </div>
        </div>
      ) : null}
    </>
  )
}