import { useEffect, useState, type FormEvent } from 'react'
import { useNavigate } from 'react-router-dom'

import { useCaseNotes } from '../../../notes/application/useCaseNotes'
import { NoteList } from '../../../notes/ui/components/NoteList'
import { useCaseParticipants } from '../../../participants/application/useCaseParticipants'
import { ParticipantList } from '../../../participants/ui/components/ParticipantList'
import type { Party } from '../../../parties/domain/party'
import type { CaseDraft } from '../../domain/caseDraft'
import type { CaseDetail } from '../../domain/caseDetail'
import { caseStatusLabels, caseStatusOptions } from '../../domain/casePresentation'
import { deleteCase, getCaseDetail, updateCase } from '../../infrastructure/caseApi'
import {
  attentionBadgeClasses,
  dangerButtonClasses,
  inputClasses,
  primaryButtonClasses,
  secondaryButtonClasses,
  statusBadgeClasses,
} from '../caseTheme'

type Tab = 'notes' | 'participants'

type ContentProps = {
  detail: CaseDetail
  parties: Party[]
  onDetailUpdate: (patch: Pick<CaseDetail, 'title' | 'status'>) => void
}

function CaseDetailContent({ detail, parties, onDetailUpdate }: ContentProps) {
  const navigate = useNavigate()
  const [activeTab, setActiveTab] = useState<Tab>('notes')
  const [isEditing, setIsEditing] = useState(false)
  const notes = detail.notes ?? []
  const participants = detail.participants ?? []
  const [editDraft, setEditDraft] = useState<CaseDraft>({
    title: detail.title,
    status: detail.status as CaseDraft['status'],
  })
  const [editError, setEditError] = useState('')
  const [showDeleteConfirm, setShowDeleteConfirm] = useState(false)
  const [deleteError, setDeleteError] = useState('')

  const notesHook = useCaseNotes(detail.id, notes)
  const participantsHook = useCaseParticipants(detail.id, participants)

  function startEditing() {
    setEditDraft({ title: detail.title, status: detail.status as CaseDraft['status'] })
    setEditError('')
    setIsEditing(true)
  }

  function cancelEditing() {
    setIsEditing(false)
    setEditError('')
  }

  async function handleSubmitEdit(e: FormEvent<HTMLFormElement>) {
    e.preventDefault()
    setEditError('')
    try {
      const updated = await updateCase(detail.id, editDraft)
      onDetailUpdate({ title: updated.title, status: updated.status })
      setIsEditing(false)
    } catch {
      setEditError('Nije moguće ažurirati predmet.')
    }
  }

  async function handleDelete() {
    setDeleteError('')
    try {
      await deleteCase(detail.id)
      navigate('/cases')
    } catch {
      setDeleteError('Nije moguće obrisati predmet.')
      setShowDeleteConfirm(false)
    }
  }

  return (
    <section className="overflow-hidden rounded-[2rem] border border-white/70 bg-white/75 shadow-[0_30px_80px_rgba(15,23,42,0.12)] backdrop-blur">
      <div className="border-b border-stone-100 bg-white/95 px-6 py-5 backdrop-blur">
        {isEditing ? (
          <form onSubmit={(e) => void handleSubmitEdit(e)} className="space-y-4">
            <div className="flex items-center justify-between gap-4">
              <p className="text-sm font-semibold uppercase tracking-[0.3em] text-orange-700">Uređivanje</p>
              <button
                type="button"
                className="rounded-full p-2 text-slate-400 transition-colors hover:bg-stone-100 hover:text-slate-700"
                onClick={cancelEditing}
                aria-label="Otkaži"
              >
                <svg width="18" height="18" viewBox="0 0 18 18" fill="none">
                  <path d="M3 3L15 15M15 3L3 15" stroke="currentColor" strokeWidth="2" strokeLinecap="round" />
                </svg>
              </button>
            </div>
            {editError ? (
              <p className="rounded-xl border border-rose-200 bg-rose-50 px-3 py-2 text-sm font-semibold text-rose-700" role="alert">
                {editError}
              </p>
            ) : null}
            <div className="grid gap-4 sm:grid-cols-[1fr_200px]">
              <div className="space-y-1.5">
                <label className="text-sm font-semibold text-slate-700" htmlFor="edit-title">
                  Naziv
                </label>
                <input
                  className={inputClasses}
                  id="edit-title"
                  type="text"
                  value={editDraft.title}
                  onChange={(e) => setEditDraft({ ...editDraft, title: e.target.value })}
                  autoFocus
                />
              </div>
              <div className="space-y-1.5">
                <label className="text-sm font-semibold text-slate-700" htmlFor="edit-status">
                  Status
                </label>
                <select
                  className={inputClasses}
                  id="edit-status"
                  value={editDraft.status}
                  onChange={(e) => setEditDraft({ ...editDraft, status: e.target.value as CaseDraft['status'] })}
                >
                  {caseStatusOptions.map((s) => (
                    <option key={s} value={s}>
                      {caseStatusLabels[s]}
                    </option>
                  ))}
                </select>
              </div>
            </div>
            <div className="flex gap-3">
              <button type="submit" className={primaryButtonClasses}>
                Spremi izmjene
              </button>
              <button type="button" className={secondaryButtonClasses} onClick={cancelEditing}>
                Otkaži
              </button>
            </div>
          </form>
        ) : (
          <>
            <div className="flex flex-wrap items-start justify-between gap-4">
              <div className="min-w-0 flex-1">
                <p className="text-xs font-semibold uppercase tracking-[0.3em] text-slate-400">Predmet #{detail.id}</p>
                <div className="mt-2 flex flex-wrap items-center gap-2">
                  <h1 className="font-display text-2xl text-slate-950 sm:text-3xl">{detail.title}</h1>
                </div>
                <div className="mt-2 flex flex-wrap gap-2">
                  <span
                    className={`rounded-full px-3 py-1 text-xs font-semibold uppercase tracking-[0.18em] ${statusBadgeClasses[detail.status] ?? 'bg-slate-200 text-slate-800 ring-1 ring-inset ring-slate-300'}`}
                  >
                    {detail.status}
                  </span>
                  <span
                    className={`rounded-full px-3 py-1 text-xs font-semibold uppercase tracking-[0.16em] ${attentionBadgeClasses[detail.attentionLevel] ?? 'bg-stone-200 text-stone-700 ring-1 ring-inset ring-stone-300'}`}
                  >
                    {detail.attentionLevel}
                  </span>
                </div>
              </div>

              <div className="flex shrink-0 items-center gap-2">
                <button type="button" className={secondaryButtonClasses} onClick={startEditing}>
                  Uredi
                </button>
                {showDeleteConfirm ? (
                  <>
                    <span className="text-sm font-semibold text-rose-700">Obrisati?</span>
                    <button type="button" className={dangerButtonClasses} onClick={() => void handleDelete()}>
                      Potvrdi
                    </button>
                    <button type="button" className={secondaryButtonClasses} onClick={() => setShowDeleteConfirm(false)}>
                      Otkaži
                    </button>
                  </>
                ) : (
                  <button type="button" className={dangerButtonClasses} onClick={() => setShowDeleteConfirm(true)}>
                    Obriši
                  </button>
                )}
              </div>
            </div>

            {deleteError ? (
              <p className="mt-3 rounded-xl border border-rose-200 bg-rose-50 px-3 py-2 text-sm font-semibold text-rose-700" role="alert">
                {deleteError}
              </p>
            ) : null}

            <div className="mt-4 flex gap-1">
              <button
                type="button"
                className={`rounded-full px-4 py-1.5 text-sm font-semibold transition-colors ${activeTab === 'notes' ? 'bg-slate-950 text-white' : 'text-slate-500 hover:bg-stone-100 hover:text-slate-900'}`}
                onClick={() => setActiveTab('notes')}
              >
                Bilješke ({notesHook.notes.length})
              </button>
              <button
                type="button"
                className={`rounded-full px-4 py-1.5 text-sm font-semibold transition-colors ${activeTab === 'participants' ? 'bg-slate-950 text-white' : 'text-slate-500 hover:bg-stone-100 hover:text-slate-900'}`}
                onClick={() => setActiveTab('participants')}
              >
                Učesnici ({participantsHook.participants.length})
              </button>
            </div>
          </>
        )}
      </div>

      {!isEditing ? (
        <div className="p-6 sm:p-8">
          {activeTab === 'notes' ? (
            <NoteList
              notes={notesHook.notes}
              addDraft={notesHook.addDraft}
              onAddDraftChange={notesHook.setAddDraft}
              onSubmitAdd={notesHook.submitAdd}
              editingNoteId={notesHook.editingNoteId}
              editContent={notesHook.editContent}
              onEditContentChange={notesHook.setEditContent}
              onStartEditing={notesHook.startEditing}
              onCancelEditing={notesHook.cancelEditing}
              onSubmitEdit={notesHook.submitEdit}
              onDelete={notesHook.removeNote}
              errors={notesHook.errors}
            />
          ) : (
            <ParticipantList
              participants={participantsHook.participants}
              addDraft={participantsHook.addDraft}
              onAddDraftChange={participantsHook.setAddDraft}
              onSubmitAdd={participantsHook.submitAdd}
              editingPartyId={participantsHook.editingPartyId}
              editRole={participantsHook.editRole}
              onEditRoleChange={participantsHook.setEditRole}
              onStartEditing={participantsHook.startEditing}
              onCancelEditing={participantsHook.cancelEditing}
              onSubmitEdit={participantsHook.submitEdit}
              onRemove={participantsHook.removeParticipantById}
              errors={participantsHook.errors}
              parties={parties}
            />
          )}
        </div>
      ) : null}
    </section>
  )
}

type PanelProps = {
  caseId: number
  parties: Party[]
}

export function CaseDetailPanel({ caseId, parties }: PanelProps) {
  const [detail, setDetail] = useState<CaseDetail | null>(null)
  const [loading, setLoading] = useState(true)
  const [loadError, setLoadError] = useState('')

  useEffect(() => {
    setLoading(true)
    setLoadError('')
    setDetail(null)

    const load = async () => {
      try {
        const data = await getCaseDetail(caseId)
        setDetail(data)
      } catch {
        setLoadError('Nije moguće učitati detalje predmeta.')
      } finally {
        setLoading(false)
      }
    }

    void load()
  }, [caseId])

  if (loading) {
    return (
      <section className="rounded-[2rem] border border-white/70 bg-white/75 p-6 shadow-[0_30px_80px_rgba(15,23,42,0.12)] backdrop-blur sm:p-8">
        <p className="text-sm text-slate-500">Učitavanje detalja…</p>
      </section>
    )
  }

  if (loadError || !detail) {
    return (
      <section className="rounded-[2rem] border border-white/70 bg-white/75 p-6 shadow-[0_30px_80px_rgba(15,23,42,0.12)] backdrop-blur sm:p-8">
        <p className="text-sm font-semibold text-rose-700" role="alert">
          {loadError}
        </p>
      </section>
    )
  }

  return (
    <CaseDetailContent
      detail={detail}
      parties={parties}
      onDetailUpdate={(patch) => setDetail({ ...detail, ...patch })}
    />
  )
}
